package com.ahmetozen.incomeexpense.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ahmetozen.incomeexpense.ui.home.HomeScreen
import com.ahmetozen.incomeexpense.ui.income.IncomeScreen
import com.ahmetozen.incomeexpense.ui.expense.ExpenseScreen
import com.ahmetozen.incomeexpense.ui.tracking.TrackingScreen

@Composable
fun NavigationGraph() {

    val navController = rememberNavController()
    val startDestination = "HomeScreen"

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        composable("HomeScreen") {
            HomeScreen(navController)
        }
        composable("IncomeScreen") {
            IncomeScreen(navController)
        }
        composable("ExpenseScreen") {
            ExpenseScreen(navController)
        }

        composable("TrackingScreen") {
            TrackingScreen(navController)
        }

    }
}