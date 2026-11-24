package com.example.cashregister.data.repository

import com.example.cashregister.data.model.Product
import com.example.cashregister.data.model.Purchase

class ProductRepository {


    private val products = mutableListOf(
        Product(1, "Pante", 20.44, 10),
        Product(2, "Shoes", 10.44, 100),
        Product(3, "Hats", 5.9, 30)
    )

    private val purchaseHistory = mutableListOf<Purchase>()

    fun getAllProducts(): List<Product> = products.toList()

    fun getProductById(id: Int): Product? = products.find { it.id == id }

    fun updateQuantity(productId: Int, newQuantity: Int) {
        products.find { it.id == productId }?.quantity = newQuantity
    }

    fun addPurchase(purchase: Purchase) {
        purchaseHistory.add(0, purchase)
    }

    fun getHistory(): List<Purchase> = purchaseHistory.toList()

    fun restock(productId: Int, amountToAdd: Int) {
        products.find { it.id == productId }?.let {
            it.quantity += amountToAdd
        }
    }
}