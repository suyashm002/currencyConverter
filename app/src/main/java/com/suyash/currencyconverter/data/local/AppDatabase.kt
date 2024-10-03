package com.suyash.currencyconverter.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.suyash.currencyconverter.data.local.dao.RateDao
import com.suyash.currencyconverter.data.local.entity.Rate
import com.suyash.currencyconverter.data.model.CurrencyRatesConverter

@Database(entities = [Rate::class], version = 1, exportSchema = false)
@TypeConverters(CurrencyRatesConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun rateDao() : RateDao
}