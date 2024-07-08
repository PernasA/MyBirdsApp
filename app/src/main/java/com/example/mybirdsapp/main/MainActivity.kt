package com.example.mybirdsapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.viewModels.HomeViewModel
import com.example.mybirdsapp.models.room.ProductDatabase
import com.example.mybirdsapp.viewModels.BirdsListViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val homeViewModel = prepareDatabaseAndHomeViewModel()

        setContent {
            MyBirdsAppTheme (darkTheme = true, dynamicColor = false) {
                Navigation(homeViewModel.value, BirdsListViewModel(this))
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun prepareDatabaseAndHomeViewModel(): Lazy<HomeViewModel> {
        val database =
            Room.databaseBuilder(applicationContext, ProductDatabase::class.java, "product_db")
                .build()
        val dao = database.yourDao()
        return viewModels<HomeViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return HomeViewModel(dao) as T
                }
            }
        })
    }
}
