package com.example.mybirdsapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mybirdsapp.R
import com.example.mybirdsapp.models.ObservationRoute
import com.example.mybirdsapp.utils.DrawableResourcesMap
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

    fun getDrawableIdByRouteId(routeId: Int): Int {
        return DrawableResourcesMap.drawableMapObservationRoutes[routeId] ?: R.drawable.map
    }

    fun getObservationRouteById(observationRouteId: Int): ObservationRoute? {
        return observationRouteList.find { it.id == observationRouteId }
    }
}
