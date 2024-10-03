package com.suyash.currencyconverter.ui.offlinecurrency

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.ui.base.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun currencyRateScreen(
    viewModel: offlineCurrencyrateViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    @Composable
    fun OfflineCurrencyScreen(uiState: UiState<List<Rate>>) {
        when(uiState) {
            is UiState.Success -> {

            }

            is UiState.Loading -> {

            }

            is UiState.Error -> {

            }
        }
    }
}