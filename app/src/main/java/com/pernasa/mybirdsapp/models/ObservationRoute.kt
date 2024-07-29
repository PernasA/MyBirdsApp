package com.pernasa.mybirdsapp.models

import com.google.gson.annotations.SerializedName

data class ObservationRoute(
    val id: Int,
    val name: String,
    val description: String,
    @SerializedName("coordinates_list")
    val coordinatesList: List<Coordinates>
)

data class Coordinates(
    val x: Double,
    val y: Double
)
