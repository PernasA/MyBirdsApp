package com.example.mybirdsapp.models.room

data class HomeState(
    val products: List<Product> = emptyList(),
    val productName: String = "",
    val productPrice: String = "",
    val productId: String? = null
)
