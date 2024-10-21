package com.suyash.currencyconverter.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.suyash.currencyconverter.data.model.CurrencyRatesConverter

@Entity(tableName = "rate")
data class Rate(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "rate_id")
    val id: Int =0,
    @ColumnInfo(name = "base")
    val base: String ="",
    @TypeConverters(CurrencyRatesConverter::class)
    val currencyRates: Map<String, Float>? = null,
)
