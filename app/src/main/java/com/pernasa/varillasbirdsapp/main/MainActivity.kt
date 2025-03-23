package com.pernasa.varillasbirdsapp.main

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.pernasa.varillasbirdsapp.ui.theme.VarillasBirdsAppTheme
import com.pernasa.varillasbirdsapp.models.room.RoomBirdsDatabase
import com.pernasa.varillasbirdsapp.viewModels.BirdsListViewModel
import com.pernasa.varillasbirdsapp.viewModels.ObservationRoutesViewModel

class MainActivity : BaseActivity() {
    private val prefsName = "AppPrefs"
    private val launchCounter = "launch_counter"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val sharedPreferences = getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        val birdsListViewModel = prepareDatabaseAndBirdsListViewModel(this, sharedPreferences)
        val observationRoutesViewModel = ObservationRoutesViewModel(this)

        val launchCount = sharedPreferences.getInt(launchCounter, 8) - 1
        sharedPreferences.edit().putInt(launchCounter, launchCount).apply()

        if (launchCount <= 0) {
            showRateAppDialog(sharedPreferences)
        }

        setContent {
            VarillasBirdsAppTheme () {
                Navigation(
                    birdsListViewModel.value,
                    observationRoutesViewModel
                )
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    private fun prepareDatabaseAndBirdsListViewModel(context: Context, sharedPreferences: SharedPreferences)
    : Lazy<BirdsListViewModel> {
        val database =
            Room.databaseBuilder(applicationContext, RoomBirdsDatabase::class.java, "birds_observed_list_db")
                .fallbackToDestructiveMigration()
                .build()
        val dao = database.yourDao()
        return viewModels<BirdsListViewModel>(factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return BirdsListViewModel(context, dao, sharedPreferences) as T
                }
            }
        })
    }

    private fun showRateAppDialog(sharedPreferences: SharedPreferences) {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setTitle("¡Gracias por usar la aplicación!")
        dialogBuilder.setMessage("Si te gusta la aplicación, por favor califícala en Google Play. Me ayuda mucho a seguir mejorándola :)")

        dialogBuilder.setPositiveButton("Calificar") { _, _ ->
            openGooglePlayStore()
            sharedPreferences.edit().putInt(launchCounter, 50).apply()
        }

        dialogBuilder.setNegativeButton("Más tarde") { dialog, _ ->
            dialog.dismiss()
            sharedPreferences.edit().putInt(launchCounter, 15).apply()
        }

        dialogBuilder.setNeutralButton("Ya la valoré") { dialog, _ ->
            dialog.dismiss()
            sharedPreferences.edit().putInt(launchCounter, 900).apply()
        }
        dialogBuilder.create().show()
    }

    private fun openGooglePlayStore() {
        val appPackageName = packageName
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (e: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }
    }
}
