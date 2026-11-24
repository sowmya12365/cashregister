package com.example.cashregister.data.model

data class Purchase(
    val productName: String,
    val quantityBought: Int,
    val totalPrice: Double,
    val purchaseDate: String
)