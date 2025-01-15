package com.ahmetozen.incomeexpense.ui.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmetozen.incomeexpense.ui.expense.ExpenseViewModel
import com.ahmetozen.incomeexpense.ui.income.IncomeViewModel

@Composable
fun HomeScreen(
    navController: NavController
) {
    val incomeViewModel = hiltViewModel<IncomeViewModel>()
    val expenseViewModel = hiltViewModel<ExpenseViewModel>()
    val totalIncome by incomeViewModel.total.collectAsState(0.0)
    val totalExpense by expenseViewModel.total.collectAsState(0.0)

    val safeIncome = totalIncome ?: 0.0
    val safeExpense = totalExpense ?: 0.0

    LaunchedEffect(Unit) {
        incomeViewModel.getTotal()
        expenseViewModel.getTotal()
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
                    text = "Toplam Gelir: ${String.format("%.2f", safeIncome)} ₺",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF388E3C)
                )
                Text(
                    text = "Toplam Gider: ${String.format("%.2f", safeExpense)} ₺",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFFD32F2F)
                )
                Text(
                    text = "Kalan Bakiye: ${String.format("%.2f", safeIncome - safeExpense)} ₺",
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
