package com.ahmetozen.incomeexpense.ui.tracking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ahmetozen.incomeexpense.ui.expense.ExpenseViewModel
import com.ahmetozen.incomeexpense.ui.income.IncomeViewModel



@Composable
fun TrackingScreen(navController: NavController){
    val viewModel= hiltViewModel<TrackingViewModel>()
    val expenses=viewModel.expenses.collectAsState(emptyList())
    val incomes=viewModel.incomes.collectAsState(emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Başlık
        Text(
            text = "Geçmiş Kayıtlar",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Gelir Listesi
        Text(
            text = "Gelirler",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(0.5f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(incomes.value) { income ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(
                            MaterialTheme.colorScheme.surface,
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = income.category, style = MaterialTheme.typography.bodyMedium)
                        Text(
                            text = "${income.amount}₺",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color.Green
                        )
                        income.description?.let {
                            Text(text = it, style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }
        }
        Text(
            text = "Giderler",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        LazyColumn(
            modifier = Modifier.fillMaxWidth().fillMaxHeight(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(expenses.value) { expense ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .background(MaterialTheme.colorScheme.surface, shape = RoundedCornerShape(8.dp))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = expense.category, style = MaterialTheme.typography.bodyMedium)
                        Text(text = "${expense.amount}₺", style = MaterialTheme.typography.bodyMedium, color = Color.Red)
                        expense.description?.let {
                            Text(text = it, style = MaterialTheme.typography.titleLarge)
                        }
                    }
                }
            }
        }
    }
}


