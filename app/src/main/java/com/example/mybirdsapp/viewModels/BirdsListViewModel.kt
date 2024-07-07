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
    private val _dataList = MutableLiveData<List<Bird>>()
    val dataBirdsList: LiveData<List<Bird>> = _dataList

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val data = loadJsonFromAssets(context, "birds_list.json")
            _dataList.postValue(data)
        }
    }
}
