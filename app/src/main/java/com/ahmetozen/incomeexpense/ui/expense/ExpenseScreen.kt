package com.ahmetozen.incomeexpense.ui.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmetozen.incomeexpense.data.model.ExpenseEntity

@Composable
fun ExpenseScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<ExpenseViewModel>()
    val total = viewModel.total.collectAsState()
    val expenses = viewModel.expenses.collectAsState(emptyList())

    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Başlık
        Text(
            text = "Gider Ekle",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        // Kategori Girişi
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Kategori") },
            placeholder = { Text("Örneğin: Yemek, Ev, Sinema") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tutar Girişi
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Tutar") },
            placeholder = { Text("Örneğin: 250") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Açıklama Girişi
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Açıklama (Opsiyonel)") },
            placeholder = { Text("Örneğin: Akşam yemeği") },
            modifier = Modifier.fillMaxWidth()
        )

        // Toplam Harcama Bilgisi
        Text(
            text = "Toplam Harcama: ${String.format("%.2f", total.value)}₺",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Red,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Ekle Butonu
        Button(
            onClick = {
                val expenseAmount = amount.toDoubleOrNull()
                if (category.isNotEmpty() && expenseAmount != null && expenseAmount > 0) {
                    val expense = ExpenseEntity(
                        id = 0, // ID veritabanı tarafından atanır
                        category = category,
                        amount = expenseAmount,
                        description = description.ifEmpty { null },
                        createdAt = System.currentTimeMillis()
                    )
                    viewModel.insert(expense) // Gideri ViewModel üzerinden ekle
                    viewModel.getAll() // Listeyi güncelle
                    viewModel.getTotal() // Toplamı güncelle
                    category = ""
                    amount = ""
                    description = ""
                } else {
                    // Hata durumunda kullanıcıya bilgi verilebilir
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = category.isNotEmpty() && amount.isNotEmpty()
        ) {
            Text("Ekle")
        }

        // Gider Listesi Başlığı
        Text(
            text = "Mevcut Giderler",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Giderler Listesi
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(expenses.value) { expense ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(
                            modifier = Modifier.weight(1f) // Kolon sol tarafta yer kaplamaya devam eder
                        ) {
                            Text(
                                text = expense.category,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "${String.format("%.2f", expense.amount)}₺",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.Green
                            )
                            expense.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp)) // Kolon ile buton arasında boşluk bırakır
                        IconButton(
                            onClick = { viewModel.delete(expense) },
                            modifier = Modifier.size(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Sil",
                                tint = Color.Red
                            )
                        }
                    }
                }
            }
        }
    }
}




