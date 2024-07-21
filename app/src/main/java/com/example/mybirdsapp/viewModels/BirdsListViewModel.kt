package com.example.mybirdsapp.viewModels

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.models.room.RoomBird
import com.example.mybirdsapp.models.room.RoomBirdsDao
import com.example.mybirdsapp.utils.loadJsonBirdsFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class BirdsListViewModel(
    context: Context,
    private val roomBirdsDao: RoomBirdsDao
) : ViewModel() {
    var dataBirdsList: List<Bird> = emptyList()
    var listObservedBirds: List<RoomBird> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val birdJsonList = loadJsonBirdsFromAssets(context, "birds_list.json")
            dataBirdsList = birdJsonList.map { birdJson ->
                Bird(
                    name = birdJson.name,
                    id = birdJson.id,
                    heightLocation = birdJson.heightLocation,
                    height = birdJson.height,
                    frequency = birdJson.frequency,
                    risk = birdJson.risk,
                    scientificName = birdJson.scientificName,
                    englishName = birdJson.englishName,
                    description = birdJson.description,
                    imageResId = getDrawableIdByName(context, birdJson.imageName)
                )
            }

            viewModelScope.launch {
                listObservedBirds = roomBirdsDao.getAllBirds().first()
                if (listObservedBirds.isEmpty()){
                    createFileAllBirds(dataBirdsList.size)
                    GlobalCounterBirdsObserved.setCounter(0)
                } else {
                    val quantityBirdsWereObserved = listObservedBirds.count { it.wasObserved }
                    GlobalCounterBirdsObserved.setCounter(quantityBirdsWereObserved)
                }
            }
        }
    }

    private fun getDrawableIdByName(context: Context, imageName: String): Int {
        try {
            val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            if (resourceId == 0) {
                throw Resources.NotFoundException("Resource not found")
            }
            return resourceId
        } catch (e: Resources.NotFoundException) {
            Log.e("ImageError", "Imagen no encontrada: $imageName", e)
            return R.drawable.orange_bird_draw
        }
    }

    fun getBirdById(birdId: Int): Bird? {
        return dataBirdsList.find { it.id == birdId }
    }

    fun editBirdWasObserved(roomBird: RoomBird) {
        viewModelScope.launch {
            listObservedBirds[roomBird.id-1].wasObserved = roomBird.wasObserved
            roomBirdsDao.editWasObservedBird(roomBird)
        }
    }

    private fun createFileAllBirds(quantityOfBirds: Int) {
        listObservedBirds = List(quantityOfBirds) { index ->
            RoomBird(id = index + 1, wasObserved = false)
        }
        viewModelScope.launch {
            roomBirdsDao.insertAll(listObservedBirds)
        }
    }
}
