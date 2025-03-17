package com.example.converter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.converter.database.CurrencyDatabase
import com.example.converter.ui.theme.ConverterTheme

class MainActivity : ComponentActivity() {
    private lateinit var database: CurrencyDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        database = CurrencyDatabase(this)
        
        setContent {
            ConverterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CurrencyConverter(database)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyConverter(database: CurrencyDatabase) {
    var amount by remember { mutableStateOf("") }
    var fromCurrency by remember { mutableStateOf("RUB") }
    var toCurrency by remember { mutableStateOf("USD") }
    var result by remember { mutableStateOf("") }
    var fromExpanded by remember { mutableStateOf(false) }
    var toExpanded by remember { mutableStateOf(false) }

    val currencies = listOf("RUB", "USD", "EUR")
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Конвертер валют",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = amount,
            onValueChange = { amount = it },
            label = { Text("Сумма") },
            modifier = Modifier.fillMaxWidth()
        )

        ExposedDropdownMenuBox(
            expanded = fromExpanded,
            onExpandedChange = { fromExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = fromCurrency,
                onValueChange = { },
                readOnly = true,
                label = { Text("Из валюты") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = fromExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = fromExpanded,
                onDismissRequest = { fromExpanded = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            fromCurrency = currency
                            fromExpanded = false
                        }
                    )
                }
            }
        }

        ExposedDropdownMenuBox(
            expanded = toExpanded,
            onExpandedChange = { toExpanded = it },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = toCurrency,
                onValueChange = { },
                readOnly = true,
                label = { Text("В валюту") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = toExpanded) },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor()
            )
            ExposedDropdownMenu(
                expanded = toExpanded,
                onDismissRequest = { toExpanded = false }
            ) {
                currencies.forEach { currency ->
                    DropdownMenuItem(
                        text = { Text(currency) },
                        onClick = {
                            toCurrency = currency
                            toExpanded = false
                        }
                    )
                }
            }
        }

        Button(
            onClick = {
                val amountValue = amount.toDoubleOrNull() ?: 0.0
                val convertedAmount = convertCurrency(amountValue, fromCurrency, toCurrency, database)
                result = String.format("%.2f %s", convertedAmount, toCurrency)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Конвертировать")
        }

        if (result.isNotEmpty()) {
            Text(
                text = "Результат: $result",
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

fun convertCurrency(amount: Double, from: String, to: String, database: CurrencyDatabase): Double {
    val fromRate = database.getRate(from)
    val toRate = database.getRate(to)
    
    val rubAmount = amount / fromRate
    return rubAmount * toRate
}