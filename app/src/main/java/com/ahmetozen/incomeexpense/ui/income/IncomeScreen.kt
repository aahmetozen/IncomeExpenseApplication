package com.ahmetozen.incomeexpense.ui.income

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
import androidx.compose.runtime.getValue
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ahmetozen.incomeexpense.data.model.IncomeEntity

@Composable
fun IncomeScreen(
    navController: NavController
) {
    val viewModel = hiltViewModel<IncomeViewModel>()
    val total = viewModel.total.collectAsStateWithLifecycle()
    val incomes = viewModel.incomes.collectAsStateWithLifecycle()

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
            text = "Gelir Ekle",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )

        // Kategori Girişi
        OutlinedTextField(
            value = category,
            onValueChange = { category = it },
            label = { Text("Kategori") },
            placeholder = { Text("Örneğin: Maaş, Borsa Kazancı") },
            modifier = Modifier.fillMaxWidth()
        )

        // Tutar Girişi
        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Tutar") },
            placeholder = { Text("Örneğin: 5000") },
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        // Açıklama Girişi
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Açıklama (Opsiyonel)") },
            placeholder = { Text("Örneğin: Şirket maaşı") },
            modifier = Modifier.fillMaxWidth()
        )

        // Toplam Gelir Bilgisi
        Text(
            text = "Toplam Gelir: ${String.format("%.2f", total.value)}₺",
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color(0xFF388E3C), // Yeşil
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Ekle Butonu
        Button(
            onClick = {
                navController.navigate("HomeScreen")
                val incomeAmount = amount.toDoubleOrNull()
                if (category.isNotEmpty() && incomeAmount != null && incomeAmount > 0) {
                    val income = IncomeEntity(
                        id = 0,
                        category = category,
                        amount = incomeAmount,
                        description = description.ifEmpty { null },
                        createdAt = System.currentTimeMillis()
                    )
                    viewModel.insert(income)
                    viewModel.getAll()
                    viewModel.getTotal()
                    category = ""
                    amount = ""
                    description = ""
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = category.isNotEmpty() && amount.isNotEmpty()
        ) {
            Text("Ekle", style = MaterialTheme.typography.bodyLarge)
        }

        // Gelir Listesi
        Text(
            text = "Mevcut Gelirler",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        // Gelir Kartları
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(incomes.value) { income ->
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
                                text = income.category,
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "${String.format("%.2f", income.amount)}₺",
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF388E3C)
                            )
                            income.description?.let {
                                Text(
                                    text = it,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(8.dp)) // Kolon ile buton arasında boşluk bırakır
                        IconButton(
                            onClick = { viewModel.delete(income) },
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

@Preview
@Composable
fun IncomeScreenPreview(){
    IncomeScreen(rememberNavController())
}


