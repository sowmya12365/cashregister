package com.example.cashregister.data.model

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    var quantity: Int
)