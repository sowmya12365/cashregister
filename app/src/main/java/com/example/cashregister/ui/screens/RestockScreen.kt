package com.example.cashregister.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.data.model.Product
import com.example.cashregister.viewmodel.CashRegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RestockScreen(
    viewModel: CashRegisterViewModel,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    var quantityInput by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignment_1") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00BCD4),
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = if (selectedProduct != null) "Selected: ${selectedProduct?.name} (Current Stock: ${selectedProduct?.quantity})" else "Please select a product below",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = if (selectedProduct != null) Color(0xFF00838F) else Color.Red
            )

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedTextField(
                value = quantityInput,
                onValueChange = { newValue ->
                    if (newValue.all { it.isDigit() }) {
                        quantityInput = newValue
                    }
                },
                label = { Text("Enter New Quantity") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Button(
                    onClick = {
                        val product = selectedProduct
                        val amount = quantityInput.toIntOrNull()

                        if (product == null || amount == null) {
                            Toast.makeText(context, "All fields are REQUIRED", Toast.LENGTH_SHORT).show()
                            return@Button
                        }

                        val success = viewModel.restock(product.id, amount) { error ->
                            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                        }

                        if (success) {
                            selectedProduct = null
                            quantityInput = ""
                            onBack()
                        }
                    },
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
                ) {
                    Text("OK", color = Color.White)
                }

                Button(
                    onClick = onBack,
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
                ) {
                    Text("CANCEL", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            LazyColumn(
                modifier = Modifier.weight(1f)
            ) {
                items(viewModel.products) { product ->
                    val isSelected = selectedProduct?.id == product.id
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedProduct = product }
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(
                                text = product.name,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                color = if (isSelected) Color(0xFF00568F) else Color.Unspecified
                            )
                            Text(String.format("%.2f", product.price), color = Color.Gray)
                        }
                        Text(
                            text = product.quantity.toString(),
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                        )
                    }
                }
            }
        }
    }
}