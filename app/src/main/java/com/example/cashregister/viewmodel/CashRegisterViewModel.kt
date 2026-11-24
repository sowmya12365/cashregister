package com.example.cashregister.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.cashregister.data.model.Product
import com.example.cashregister.data.model.Purchase
import com.example.cashregister.data.repository.ProductRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CashRegisterViewModel : ViewModel() {

    private val repository = ProductRepository()

    // UI state
    var products by mutableStateOf(repository.getAllProducts())
        private set

    var selectedProduct by mutableStateOf<Product?>(null)
        private set

    var quantityText by mutableStateOf("")
        private set

    var totalText by mutableStateOf("0.00")
        private set

    var history by mutableStateOf(repository.getHistory())
        private set

    // ---- Home screen actions ----

    fun selectProduct(product: Product) {
        selectedProduct = product
        calculateTotal()
    }

    fun onNumberClicked(number: String) {
        if (number == "C") {
            quantityText = ""
        } else {
            quantityText += number
        }
        calculateTotal()
    }

    private fun calculateTotal() {
        val qty = quantityText.toIntOrNull() ?: 0
        val price = selectedProduct?.price ?: 0.0
        totalText = String.format(Locale.US, "%.2f", qty * price)
    }

    fun buy(onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        val product = selectedProduct
        val qty = quantityText.toIntOrNull()

        if ((product == null) || (qty == null) || (qty == 0)) {
            onError("All fields are required!!")
            return
        }

        if (qty > product.quantity) {
            onError("No enough quantity in the stock!!")
            return
        }

        // Update stock
        repository.updateQuantity(product.id, product.quantity - qty)

        // Save to history
        val date = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US).format(Date())
        val purchase = Purchase(
            productName = product.name,
            quantityBought = qty,
            totalPrice = qty * product.price,
            purchaseDate = date,
        )
        repository.addPurchase(purchase)

        // Refresh lists
        products = repository.getAllProducts()
        history = repository.getHistory()

        // Reset UI
        selectedProduct = null
        quantityText = ""
        totalText = "0.00"

        val formattedPrice = String.format(Locale.US, "%.2f", purchase.totalPrice)
        onSuccess("Thank You for your purchase\nYour purchase is $qty ${product.name} for $formattedPrice")
    }

    // ---- Restock ----
    fun restock(productId: Int, amount: Int, onError: (String) -> Unit): Boolean {
        if (amount <= 0) {
            onError("All fields are REQUIRED")
            return false
        }
        repository.restock(productId, amount)
        products = repository.getAllProducts()
        return true
    }

    fun refreshProducts() {
        products = repository.getAllProducts()
    }
}