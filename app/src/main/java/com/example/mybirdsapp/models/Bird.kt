package com.example.mybirdsapp.models

data class Bird (
    val id: Int = 0,
    val name: String = "",
    val heightLocation: Int = 0,
    val height: Int = 0,
    val frequency: Int = 0,
    val risk: String = "",
    val scientificName: String = "",
    val englishName: String = "",
    val description: String = "",
    val imageResId: Int = 0,
    val wasObserved: Boolean = false
)
