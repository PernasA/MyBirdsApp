package com.example.mybirdsapp.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mybirdsapp.R
import com.example.mybirdsapp.ui.theme.MyBirdsAppTheme
import com.example.mybirdsapp.viewModels.HomeViewModel
import com.example.mybirdsapp.models.room.ProductDatabase
import com.example.mybirdsapp.viewModels.BirdsListViewModel

/**
 * enum values that represent the screens in the app
 */
enum class NameOfScreen(@StringRes val title: Int) {
    Start(title = R.string.title_main_page_appbar),
    BirdsPage(title = R.string.birds_page_title),
    ObservationRoutesPage(title = R.string.observation_routes_page_title),
    AboutUsPage(title = R.string.about_us_page_title)
}

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