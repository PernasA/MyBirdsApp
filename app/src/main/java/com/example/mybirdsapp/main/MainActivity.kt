package com.example.mybirdsapp.main

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.models.room.RoomBirdsDatabase
import com.example.mybirdsapp.viewModels.BirdsListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val birdsListViewModel = prepareDatabaseAndBirdsListViewModel(this)

        setContent {
            MyBirdsAppTheme (darkTheme = true, dynamicColor = false) {
                Navigation(birdsListViewModel.value)
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
