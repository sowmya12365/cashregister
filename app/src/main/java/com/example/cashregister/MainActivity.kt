package com.example.cashregister

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.cashregister.navigation.Routes
import com.example.cashregister.ui.screens.*
import com.example.cashregister.ui.theme.CashRegisterTheme
import com.example.cashregister.viewmodel.CashRegisterViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CashRegisterTheme {
                CashRegisterApp()
            }
        }
    }
}

@Composable
fun CashRegisterApp() {
    val navController = rememberNavController()
    val viewModel: CashRegisterViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = Routes.HOME,
    ) {
        composable(Routes.HOME) {
            HomeScreen(
                viewModel = viewModel,
                onManagerClick = { navController.navigate(Routes.MANAGER) },
            )
        }

        composable(Routes.MANAGER) {
            ManagerScreen(
                onHistoryClick = { navController.navigate(Routes.HISTORY) },
                onRestockClick = { navController.navigate(Routes.RESTOCK) },
                onBack = { navController.popBackStack() },
            )
        }

        composable(Routes.HISTORY) {
            HistoryScreen(
                viewModel = viewModel,
                onItemClick = { index ->
                    navController.navigate(Routes.historyDetail(index))
                },
                onBack = { navController.popBackStack() },
            )
        }

        composable(
            route = Routes.HISTORY_DETAIL,
            arguments = listOf(navArgument("index") { type = NavType.IntType }),
        ) { backStackEntry ->
            val index = backStackEntry.arguments?.getInt("index") ?: 0
            viewModel.history.getOrNull(index)?.let { purchase ->
                HistoryDetailScreen(purchase = purchase) {
                    navController.popBackStack()
                }
            }
        }

        composable(Routes.RESTOCK) {
            RestockScreen(
                viewModel = viewModel,
                onBack = {
                    viewModel.refreshProducts()
                    navController.popBackStack()
                },
            )
        }
    }
}