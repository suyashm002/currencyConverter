package com.suyash.currencyconverter.data.local

import com.suyash.currencyconverter.data.local.entity.Rate
import kotlinx.coroutines.flow.Flow

interface DatabaseService {

    fun getApiResponse(): Flow<Rate>

    fun deleteAllAndInsertAll(rates: Rate)
}