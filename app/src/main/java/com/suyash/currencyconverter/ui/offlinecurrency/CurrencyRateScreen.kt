package com.suyash.currencyconverter.ui.offlinecurrency

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.ui.base.UiState
import com.suyash.currencyconverter.ui.main.CurrencyConverter
import com.suyash.currencyconverter.utils.AppConstant

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CurrencyRateScreen(
    viewModel: OfflineCurrencyrateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = Color.White
        ), title = { Text(text = AppConstant.APP_NAME) })
    }, content = { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OfflineCurrencyScreen(uiState)
        }
    })
}

    @Composable
    fun OfflineCurrencyScreen(uiState: UiState<Rate>) {
        when(uiState) {
            is UiState.Success -> {
                CurrencyConverter(uiState.data) { amount, selectedCurrency ->
                // Perform the conversion
                    // Get the base currency from the Rate object
                    val baseCurrency = uiState.data.base
                    val rates = uiState.data.currencyRates

                    // 1. Find the exchange rate for the selected currency to base currency
                    val selectedCurrencyRate = rates?.get(selectedCurrency) ?: 1f
                    // 2. Convert the amount to the base currency (if the selected currency is not the base)
                    val amountInBaseCurrency = if (selectedCurrency == baseCurrency) {
                        amount
                    } else {
                        amount / selectedCurrencyRate
                    }

                    // Convert the amount in the base currency to all other currencies
                    rates?.mapValues { (currency, rateValue) ->
                        amountInBaseCurrency * rateValue
                    }
            }
            }
            is UiState.Loading -> {

            }

            is UiState.Error -> {

            }
        }
    }



