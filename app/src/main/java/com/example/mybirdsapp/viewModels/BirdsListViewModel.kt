package com.example.mybirdsapp.viewModels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybirdsapp.models.Bird
import com.example.mybirdsapp.utils.loadJsonFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BirdsListViewModel(context: Context) : ViewModel() {
    private val _dataBirdsList = MutableLiveData<List<Bird>>()
    val dataBirdsList: LiveData<List<Bird>> get() = _dataBirdsList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val birdJsonList = loadJsonFromAssets(context, "birds_list.json")
            val birdList = birdJsonList.map { birdJson ->
                Bird(
                    name = birdJson.name,
                    id = birdJson.id,
                    imageResId = getDrawableIdByName(context, birdJson.imageName)
                )
            }
            _dataBirdsList.postValue(birdList)
        }
    }

    private fun getDrawableIdByName(context: Context, imageName: String): Int {
        return context.resources.getIdentifier(imageName, "drawable", context.packageName)
    }

    fun getBirdById(birdId: Int): Bird? {
        return _dataBirdsList.value?.find { it.id == birdId }
    }
}
