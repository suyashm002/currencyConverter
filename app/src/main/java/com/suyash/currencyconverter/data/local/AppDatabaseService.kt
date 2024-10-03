package com.suyash.currencyconverter.data.local

import com.suyash.currencyconverter.data.local.entity.Rate
import kotlinx.coroutines.flow.Flow

class AppDatabaseService constructor(private val appDatabase: AppDatabase): DatabaseService {
    override fun getApiResponse(): Flow<Rate> {
        return appDatabase.rateDao().getAll()
    }

    override fun deleteAllAndInsertAll(rates: Rate) {
        appDatabase.rateDao().deleteAllAndInsertAll(rates)
    }
}