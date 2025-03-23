package com.pernasa.varillasbirdsapp.viewModels

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pernasa.varillasbirdsapp.models.Bird
import com.pernasa.varillasbirdsapp.models.GlobalCounterBirdsObserved
import com.pernasa.varillasbirdsapp.models.room.RoomBird
import com.pernasa.varillasbirdsapp.models.room.RoomBirdsDao
import com.pernasa.varillasbirdsapp.utils.DrawableResourcesList
import com.pernasa.varillasbirdsapp.utils.InterfaceJsonLoader
import com.pernasa.varillasbirdsapp.utils.JsonReader
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
    sharedPreferences: SharedPreferences,
    private val jsonReader: InterfaceJsonLoader = JsonReader()
) : ViewModel() {
    var dataBirdsList: List<Bird> = emptyList()
    lateinit var gameGuessViewModel: GameGuessViewModel

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
                    height = birdJson.height,
                    frequency = birdJson.frequency,
                    scientificName = birdJson.scientificName,
                    description = birdJson.description,
                    imageResId = getDrawableIdByBirdIdPosition(birdJson.id)
                )
            }

            gameGuessViewModel = GameGuessViewModel(dataBirdsList, sharedPreferences)

            viewModelScope.launch {
                _listObservedBirds.value = roomBirdsDao.getAllBirds().first()
                if (_listObservedBirds.value.isEmpty()){
                    createFileAllBirds(dataBirdsList.size)
                    GlobalCounterBirdsObserved.setCounter(0)
                } else {
                    val quantityBirdsWereObserved = _listObservedBirds.value.count { it.wasObserved }
                    GlobalCounterBirdsObserved.setCounter(quantityBirdsWereObserved)

                    if (_listObservedBirds.value.size < dataBirdsList.size) {
                        // Filtrar los nuevos pájaros que no están en la base de datos
                        val existingBirdIds = _listObservedBirds.value.map { it.id }.toSet()
                        val newBirds = dataBirdsList.filter { bird -> bird.id !in existingBirdIds }

                        // Convertir los nuevos pájaros a RoomBird para insertarlos
                        val newRoomBirds = newBirds.map { bird ->
                            RoomBird(id = bird.id, wasObserved = false)
                        }

                        // Insertar los nuevos pájaros en la base de datos
                        roomBirdsDao.insertAll(newRoomBirds)
                    }
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
