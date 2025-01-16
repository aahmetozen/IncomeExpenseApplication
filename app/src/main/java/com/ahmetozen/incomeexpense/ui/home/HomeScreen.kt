package com.ahmetozen.incomeexpense.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen(
    navController: NavController
) {
    val homeViewModel = hiltViewModel<HomeViewModel>()
    val totalIncome by homeViewModel.totalincome.collectAsStateWithLifecycle()
    val totalExpense by homeViewModel.totalexpense.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        homeViewModel.getTotalIncomes()
        homeViewModel.getTotalExpenses()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.padding(top = 25.dp),
            text = "Gelir-Gider Takibi",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "Toplam Gelir: ${String.format("%.2f", totalIncome)} ₺",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF388E3C)
                )
                Text(
                    text = "Toplam Gider: ${String.format("%.2f", totalExpense)} ₺",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFD32F2F)
                )
                Text(
                    text = "Kalan Bakiye: ${String.format("%.2f", (totalIncome?:0.0) - (totalExpense?:0.0))} ₺",
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(bottom = 25.dp),
            verticalArrangement = Arrangement.spacedBy(22.dp)
        ) {
            Button(colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                onClick = { navController.navigate("IncomeScreen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Gelir Ekle", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                onClick = { navController.navigate("ExpenseScreen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Gider Ekle", style = MaterialTheme.typography.titleMedium)
            }
            Button(
                onClick = { navController.navigate("TrackingScreen") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Geçmişi Görüntüle", style = MaterialTheme.typography.titleMedium)
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview(){
    HomeScreen(rememberNavController())
}
