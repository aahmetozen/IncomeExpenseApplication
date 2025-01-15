package com.ahmetozen.incomeexpense.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.ahmetozen.incomeexpense.ui.theme.IncomeExpenseTheme
import com.ahmetozen.incomeexpense.ui.navigation.NavigationGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            IncomeExpenseTheme {
                NavigationGraph()
            }
        }
    }
}
