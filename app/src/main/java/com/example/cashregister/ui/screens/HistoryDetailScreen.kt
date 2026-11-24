package com.example.cashregister.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cashregister.data.model.Purchase

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryDetailScreen(
    purchase: Purchase,
    onBack: () -> Unit
) {
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
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text("Product: ${purchase.productName}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Price: ${String.format("%.2f", purchase.totalPrice)}", fontSize = 18.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text("Purchase Date: ${purchase.purchaseDate}", fontSize = 16.sp)
        }
    }
}