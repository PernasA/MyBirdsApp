package com.pernasa.mybirdsapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pernasa.mybirdsapp.models.Bird
import com.pernasa.mybirdsapp.models.room.RoomBird
import com.pernasa.mybirdsapp.models.room.RoomBirdsDao
import com.pernasa.mybirdsapp.utils.DrawableResourcesList
import com.pernasa.mybirdsapp.utils.InterfaceJsonLoader
import com.pernasa.mybirdsapp.utils.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class BirdsListViewModel(
    context: Context,
    private val roomBirdsDao: RoomBirdsDao,
    private val jsonReader: InterfaceJsonLoader = JsonReader()
) : ViewModel() {
    var dataBirdsList: List<Bird> = emptyList()
    var listObservedBirds: List<RoomBird> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val birdJsonList = jsonReader.loadJsonBirdsFromAssets(context, "birds_list.json")
            dataBirdsList = birdJsonList.map { birdJson ->
                Bird(
                    name = birdJson.name,
                    id = birdJson.id,
                    heightLocation = birdJson.heightLocation,
                    height = birdJson.height,
                    frequency = birdJson.frequency,
                    scientificName = birdJson.scientificName,
                    englishName = birdJson.englishName,
                    description = birdJson.description,
                    imageResId = getDrawableIdByBirdIdPosition(birdJson.id)
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

    /*
    There is a list that is ordered the same way than the ids of the routes. Id route 1 = position 0
    */
    @TestOnly
    fun getDrawableIdByBirdIdPosition(birdId: Int): Int {
        return DrawableResourcesList.drawableListBirds[birdId-1]
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

    @TestOnly
    fun createFileAllBirds(quantityOfBirds: Int) {
        listObservedBirds = List(quantityOfBirds) { index ->
            RoomBird(id = index + 1, wasObserved = false)
        }
        viewModelScope.launch {
            roomBirdsDao.insertAll(listObservedBirds)
        }
    }
}
