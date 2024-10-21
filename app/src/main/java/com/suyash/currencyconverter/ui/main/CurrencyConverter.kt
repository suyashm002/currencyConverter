package com.suyash.currencyconverter.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.TextFieldValue
import com.suyash.currencyconverter.data.local.entity.Rate

@Composable
fun CurrencyConverter(
    rates: Rate ,// Map of currency rates from API
    onConvert: (Float, String) -> Unit // Callback when conversion is triggered
) {
    var selectedCurrency by remember { mutableStateOf("USD") }  // Default currency
    var amountInput by remember { mutableStateOf(TextFieldValue("")) }
    val currencyList = rates.currencyRates?.keys?.toList() // List of all available currencies

    Column(modifier = Modifier.padding(16.dp)) {
        // TextField for input amount
        TextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Enter Amount") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
                .testTag("amountInputField")
        )

        Spacer(modifier = Modifier.height(16.dp))

        // DropdownMenu to select the currency
        CurrencyDropdown(currencyList, selectedCurrency) { currency ->
            selectedCurrency = currency
        }

        Spacer(modifier = Modifier.height(16.dp))


        Spacer(modifier = Modifier.height(16.dp))

        // Display conversion results
        ConversionList(rates.currencyRates, amountInput.text, selectedCurrency)
    }
}

@Composable
fun CurrencyDropdown(
    currencies: List<String>?,
    selectedCurrency: String,
    onCurrencySelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(
            onClick = { expanded = true },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(selectedCurrency)
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth()
        ) {
            currencies?.forEach { currency ->
                DropdownMenuItem(onClick = {
                    expanded = false
                    onCurrencySelected(currency)
                },
                    text = { Text(text = currency) })
            } ?: run {
                // If currencies list is null, you can show a fallback or handle it accordingly
                DropdownMenuItem(onClick = { expanded = false },
                 text =  {Text(text = "No currencies available")})
                }
            }
        }
    }

@Composable
fun ConversionList(rates: Map<String, Float>?, amount: String, baseCurrency: String) {
    val amountFloat = amount.toFloatOrNull() ?: 0f

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(rates!!.keys.toList()) { currency ->
            val rate = rates[currency] ?: 1f
            val convertedAmount = amountFloat * rate

            // Each row of the conversion list
            ConversionRow(baseCurrency, currency, convertedAmount)
        }
    }
}

@Composable
fun ConversionRow(baseCurrency: String, targetCurrency: String, convertedAmount: Float) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = "$baseCurrency -> $targetCurrency")
        Text(text = String.format("%.2f", convertedAmount))
    }
}
