package com.suyash.currencyconverter.data.repository

import com.suyash.currencyconverter.data.api.NetworkService
import com.suyash.currencyconverter.data.local.DatabaseService
import com.suyash.currencyconverter.data.local.SharedPreferenceService
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.model.toRateEntity
import com.suyash.currencyconverter.utils.AppConstant.API_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OfflineCurrencyRateRepository @Inject constructor(
    private val networkService: NetworkService,
    private val databaseService: DatabaseService,
    private val sharedPreferenceService: SharedPreferenceService
) {

    fun getCurrencies(): Flow<Rate> = flow {
        if (!sharedPreferenceService.isMoreThanThirtyMinutes()) {
            emitAll(databaseService.getApiResponse())
        } else {
            val networkResponse = networkService.getCurrencies(API_KEY)
            val rate = networkResponse.toRateEntity()
            databaseService.deleteAllAndInsertAll(rate)
            emitAll(databaseService.getApiResponse())
        }
    }.flowOn(Dispatchers.IO)

    fun getCurrenciesDirectlyFromDB(): Flow<Rate> {
        return databaseService.getApiResponse()
    }
}