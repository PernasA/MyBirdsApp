package com.pernasa.mybirdsapp.models

import com.google.gson.annotations.SerializedName

data class BirdJson (
    val id: Int = 0,
    val name: String = "",
    val height: Int = 0,
    val frequency: Int = 0,
    val description: String = "",
    @SerializedName("height_location")
    val heightLocation: Int = 0,
    @SerializedName("scientific_name")
    val scientificName: String = "",
    @SerializedName("english_name")
    val englishName: String = ""
)
