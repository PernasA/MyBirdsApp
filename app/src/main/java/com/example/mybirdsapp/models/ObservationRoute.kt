package com.example.mybirdsapp.models

import com.google.gson.annotations.SerializedName

data class ObservationRoute(
    val name: String,
    val birds: List<BirdX>,
    val description: String,
    val id: Int,
    @SerializedName("image_name")
    val imageName: String
)

data class BirdX(
    val id: Int
)