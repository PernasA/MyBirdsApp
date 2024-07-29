package com.pernasa.mybirdsapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pernasa.mybirdsapp.models.ObservationRoute
import com.pernasa.mybirdsapp.utils.DrawableResourcesList
import com.pernasa.mybirdsapp.utils.InterfaceJsonLoader
import com.pernasa.mybirdsapp.utils.JsonReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ObservationRoutesViewModel(
    context: Context,
    private val jsonReader: InterfaceJsonLoader = JsonReader()
) : ViewModel() {
    var observationRouteList: List<ObservationRoute> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            observationRouteList =
                jsonReader.loadJsonObservationRoutesFromAssets(context, "observation_routes.json")
        }
    }

    /*
    There is a list that is ordered the same way than the ids of the routes. Id route 1 = position 0
    */
    fun getDrawableIdByRouteIdPosition(observationRouteId: Int): Int {
        return DrawableResourcesList.drawableListObservationRoutes[observationRouteId-1]
    }

    fun getObservationRouteById(observationRouteId: Int): ObservationRoute? {
        return observationRouteList.find { it.id == observationRouteId }
    }
}
