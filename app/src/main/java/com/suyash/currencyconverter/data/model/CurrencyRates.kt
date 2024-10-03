package com.suyash.currencyconverter.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import com.suyash.currencyconverter.data.local.entity.Rate

data class CurrencyRates(
  //  @ColumnInfo(name = "key")
    var key : String,
  //  @ColumnInfo(name = "value")
    var value : Float,
)
