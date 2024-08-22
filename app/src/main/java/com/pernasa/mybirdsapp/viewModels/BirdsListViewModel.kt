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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.jetbrains.annotations.TestOnly

class BirdsListViewModel(
    context: Context,
    private val roomBirdsDao: RoomBirdsDao,
    private val jsonReader: InterfaceJsonLoader = JsonReader()
) : ViewModel() {
    var dataBirdsList: List<Bird> = emptyList()

    private val _listObservedBirds = MutableStateFlow<List<RoomBird>>(emptyList())
    val listObservedBirds: StateFlow<List<RoomBird>> = _listObservedBirds

    fun getBirdObservationState(birdId: Int): Flow<Boolean> {
        return _listObservedBirds
            .map { birds -> birds.firstOrNull { it.id == birdId }?.wasObserved ?: false }
    }

    fun editBirdWasObserved(bird: RoomBird) {
        viewModelScope.launch {
            roomBirdsDao.editWasObservedBird(bird)
        }
        _listObservedBirds.update { birds ->
            birds.map {
                if (it.id == bird.id) it.copy(wasObserved = bird.wasObserved) else it
            }
        }
    }

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
                _listObservedBirds.value = roomBirdsDao.getAllBirds().first()
                if (_listObservedBirds.value.isEmpty()){
                    createFileAllBirds(dataBirdsList.size)
                    GlobalCounterBirdsObserved.setCounter(0)
                } else {
                    val quantityBirdsWereObserved = _listObservedBirds.value.count { it.wasObserved }
                    GlobalCounterBirdsObserved.setCounter(quantityBirdsWereObserved)
                }
            }
        }
    }

    @TestOnly
    fun getDrawableIdByBirdIdPosition(birdId: Int): Int {
        return DrawableResourcesList.drawableListBirds[birdId-1]
    }

    fun getBirdById(birdId: Int): Bird? {
        return dataBirdsList.find { it.id == birdId }
    }

    @TestOnly
    fun createFileAllBirds(quantityOfBirds: Int) {
        _listObservedBirds.value = List(quantityOfBirds) { index ->
            RoomBird(id = index + 1, wasObserved = false)
        }
        viewModelScope.launch {
            roomBirdsDao.insertAll(_listObservedBirds.value)
        }
    }
}
