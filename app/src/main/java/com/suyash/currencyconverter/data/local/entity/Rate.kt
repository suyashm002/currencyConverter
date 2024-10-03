package com.suyash.currencyconverter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.suyash.currencyconverter.data.model.CurrencyRates
import com.suyash.currencyconverter.data.model.CurrencyRatesConverter

@Entity(tableName = "rate")
data class Rate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rate_id")
    val id: Int =0,
    @ColumnInfo(name = "base")
    val base: String ="",
    @TypeConverters(CurrencyRatesConverter::class)
    @ColumnInfo(name = "CurrencyRates")
    val rates: CurrencyRates,
//    @ColumnInfo(name = "AFN")
//    val AFN: String = "",
//    @ColumnInfo(name = "ALL")
//    val ALL: String = "",
//    @ColumnInfo(name = "AMD")
//    val AMD: String = "",
//    @ColumnInfo(name = "ANG")
//    val ANG: String = "",
//    @ColumnInfo(name = "AOA")
//    val AOA: String = "",
//    @ColumnInfo(name = "ARS")
//    val ARS: String = "",
//    @ColumnInfo(name = "AUD")
//    val AUD: String = ""
)
//{
//    fun getRatesAsMap(): Map<String, Float> {
//        val mapType = object : TypeToken<Map<String, Float>>() {}.type
//        return Gson().fromJson(rates, mapType)
//    }
//
//    fun setRatesFromMap(map: Map<String, Float>) {
//        rates = Gson().toJson(map)
//    }
//}
