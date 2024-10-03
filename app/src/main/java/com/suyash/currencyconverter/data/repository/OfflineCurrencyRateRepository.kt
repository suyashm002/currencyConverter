package com.suyash.currencyconverter.data.repository

import android.util.Log
import com.suyash.currencyconverter.data.api.NetworkService
import com.suyash.currencyconverter.data.local.DatabaseService
import com.suyash.currencyconverter.data.local.SharedPreferenceService
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.model.ApiResponse
import com.suyash.currencyconverter.data.model.toRateEntity
import com.suyash.currencyconverter.utils.AppConstant.API_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineCurrencyRateRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val sharedPreferenceService: SharedPreferenceService
) {

    fun getCurrencies(): Flow<Rate> {
        return flow {
            emit(networkService.getCurrencies(API_KEY)) }
            .map { it -> it.toRateEntity()
            }.flatMapConcat { rates ->
                flow { emit(databaseService.deleteAllAndInsertAll(rates)) }
            }.flatMapConcat {
                Log.d("data",it.toString())
                databaseService.getApiResponse()
            }
    }

    fun getCurrenciesDirectlyFromDB(): Flow<Rate> {
        return databaseService.getApiResponse()
    }
}