package com.example.cashregister.ui.screens

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.data.model.Product
import com.example.cashregister.viewmodel.CashRegisterViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: CashRegisterViewModel,
    onManagerClick: () -> Unit
) {
    val context = LocalContext.current
    var showThankYouDialog by remember { mutableStateOf(false) }
    var thankYouMessage by remember { mutableStateOf("") }
    val isLandscape = LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Assignment_1") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF00BCD4),
                    titleContentColor = Color.White
                ),
                actions = {
                    Button(
                        onClick = onManagerClick,
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4))
                    ) {
                        Text("MANAGER", color = Color.White)
                    }
                }
            )
        }
    ) { padding ->
        if (isLandscape) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(12.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Column(
                    modifier = Modifier
                        .weight(1.2f)
                        .fillMaxHeight()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = viewModel.selectedProduct?.name ?: "Product Type",
                            fontSize = 16.sp,
                            color = Color.Gray
                        )
                        Text(
                            text = "Total: ${viewModel.totalText}",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(3f)
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            val numbers = listOf(
                                listOf("1", "2", "3"),
                                listOf("4", "5", "6"),
                                listOf("7", "8", "9"),
                                listOf("C", "0")
                            )
                            numbers.forEach { row ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                                ) {
                                    row.forEach { digit ->
                                        Button(
                                            onClick = { viewModel.onNumberClicked(digit) },
                                            modifier = Modifier
                                                .weight(1f)
                                                .height(38.dp),
                                            contentPadding = PaddingValues(0.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFF00BCD4)
                                            ),
                                            shape = MaterialTheme.shapes.extraSmall
                                        ) {
                                            Text(digit, fontSize = 16.sp, color = Color.White)
                                        }
                                    }
                                    if (row.size == 2) {
                                        Spacer(modifier = Modifier.weight(1f))
                                    }
                                }
                            }
                        }

                        Button(
                            onClick = {
                                viewModel.buy(
                                    onSuccess = { message ->
                                        thankYouMessage = message
                                        showThankYouDialog = true
                                    },
                                    onError = { error ->
                                        Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    }
                                )
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                            shape = MaterialTheme.shapes.extraSmall
                        ) {
                            Text("BUY", fontSize = 16.sp, color = Color.White, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Quantity", color = Color.Gray, fontSize = 14.sp)
                        Text(
                            text = viewModel.quantityText.ifEmpty { "0" },
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    items(viewModel.products) { product ->
                        val isSelected = viewModel.selectedProduct?.id == product.id
                        ProductRow(
                            product = product,
                            isSelected = isSelected,
                            onClick = { viewModel.selectProduct(product) }
                        )
                    }
                }
            }
        } else {
            // Portrait Layout
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = viewModel.selectedProduct?.name ?: "Product Type",
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Total\n${viewModel.totalText}",
                        fontSize = 16.sp,
                        textAlign = TextAlign.End
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Column(modifier = Modifier.weight(3f)) {
                        val numbers = listOf(
                            listOf("1", "2", "3"),
                            listOf("4", "5", "6"),
                            listOf("7", "8", "9"),
                            listOf("C", "0")
                        )
                        numbers.forEachIndexed { index, row ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                row.forEach { digit ->
                                    Button(
                                        onClick = { viewModel.onNumberClicked(digit) },
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(52.dp),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = Color(0xFF00BCD4)
                                        ),
                                        shape = MaterialTheme.shapes.extraSmall
                                    ) {
                                        Text(digit, fontSize = 20.sp, color = Color.White)
                                    }
                                }
                                if (row.size == 2) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            if (index < numbers.size - 1) {
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }

                    Button(
                        onClick = {
                            viewModel.buy(
                                onSuccess = { message ->
                                    thankYouMessage = message
                                    showThankYouDialog = true
                                },
                                onError = { error ->
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                }
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF00BCD4)),
                        shape = MaterialTheme.shapes.extraSmall
                    ) {
                        Text("BUY", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight.Bold)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Quantity", color = Color.Gray)
                Text(
                    text = viewModel.quantityText.ifEmpty { "0" },
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(viewModel.products) { product ->
                        val isSelected = viewModel.selectedProduct?.id == product.id
                        ProductRow(
                            product = product,
                            isSelected = isSelected,
                            onClick = { viewModel.selectProduct(product) }
                        )
                    }
                }
            }
        }
    }


    if (showThankYouDialog) {
        AlertDialog(
            onDismissRequest = { showThankYouDialog = false },
            title = { Text("Thank You for your purchase") },
            text = { Text(thankYouMessage) },
            confirmButton = {
                TextButton(onClick = { showThankYouDialog = false }) {
                    Text("OK")
                }
            }
        )
    }
}

@Composable
fun ProductRow(
    product: Product,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Surface(
        color = if (isSelected) Color(0xFFE0F7FA) else Color.Transparent,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = product.name,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                    color = if (isSelected) Color(0xFF00838F) else Color.Unspecified
                )
                Text(String.format("%.2f", product.price), color = Color.Gray)
            }
            Text(
                text = product.quantity.toString(),
                fontSize = 16.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}