package com.suyash.currencyconverter.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class CurrencyRatesConverter {
    @TypeConverter
    fun fromCurrencyRates(currencyRates: CurrencyRates): String {
        return Gson().toJson(currencyRates)
    }

    @TypeConverter
    fun toCurrencyRates(currencyRatesString: String): CurrencyRates {
        return Gson().fromJson(currencyRatesString, CurrencyRates::class.java)
    }
}