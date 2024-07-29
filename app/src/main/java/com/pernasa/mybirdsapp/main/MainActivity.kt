package com.pernasa.mybirdsapp.main

import android.content.Context
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.pernasa.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.pernasa.mybirdsapp.models.room.RoomBirdsDatabase
import com.pernasa.mybirdsapp.viewModels.BirdsListViewModel
import com.pernasa.mybirdsapp.viewModels.ObservationRoutesViewModel

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val birdsListViewModel = prepareDatabaseAndBirdsListViewModel(this)
        val observationRoutesViewModel = ObservationRoutesViewModel(this)
        setContent {
            MyBirdsAppTheme (darkTheme = true) {
                Navigation(birdsListViewModel.value, observationRoutesViewModel)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun prepareDatabaseAndBirdsListViewModel(context: Context): Lazy<BirdsListViewModel> {
        val database =
            Room.databaseBuilder(applicationContext, RoomBirdsDatabase::class.java, "birds_observed_list_db")
                .fallbackToDestructiveMigration()
                .build()
        val dao = database.yourDao()
        return viewModels<BirdsListViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BirdsListViewModel(context, dao) as T
                }
            }
        })
    }
}
