package com.suyash.currencyconverter.ui.offlinecurrency

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.repository.OfflineCurrencyRateRepository
import com.suyash.currencyconverter.ui.base.UiState
import com.suyash.currencyconverter.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OfflineCurrencyrateViewModel @Inject constructor(
    networkHelper: NetworkHelper,
    private val offlineCurrencyRateRepository: OfflineCurrencyRateRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<Rate>>(UiState.Loading)

    val uiState: StateFlow<UiState<Rate>> = _uiState

    init {
        if (networkHelper.isNetworkConnected()){
            fetchCurrency()
        } else {
            fetchCurrencyRatesDirectlyFromDB()
        }
    }

     fun fetchCurrency() {
        viewModelScope.launch {
            offlineCurrencyRateRepository.getCurrencies()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }

    private fun fetchCurrencyRatesDirectlyFromDB() {
        viewModelScope.launch {
            offlineCurrencyRateRepository.getCurrenciesDirectlyFromDB()
                .flowOn(Dispatchers.IO)
                .catch { e ->
                    _uiState.value = UiState.Error(e.toString())
                }.collect {
                    _uiState.value = UiState.Success(it)
                }
        }
    }
}