package com.example.mybirdsapp.viewModels

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.ObservationRoute
import com.example.mybirdsapp.utils.loadJsonObservationRoutesFromAssets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ObservationRoutesViewModel(
    context: Context,
) : ViewModel() {
    var observationRouteList: List<ObservationRoute> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observationRouteList =
                loadJsonObservationRoutesFromAssets(context, "observation_routes.json")
        }
    }

    private fun getDrawableIdByName(context: Context, imageName: String): Int {
        try {
            val resourceId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
            if (resourceId == 0) {
                throw Resources.NotFoundException("Resource not found")
            }
            return resourceId
        } catch (e: Resources.NotFoundException) {
            Log.e("ImageError", "Imagen no encontrada: $imageName", e)
            return R.drawable.map
        }
    }

    fun getObservationRouteById(observationRouteId: Int): ObservationRoute? {
        return observationRouteList.find { it.id == observationRouteId }
    }
}
