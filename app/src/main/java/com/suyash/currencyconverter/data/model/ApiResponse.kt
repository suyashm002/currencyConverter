package com.suyash.currencyconverter.data.model

import com.google.gson.annotations.SerializedName
import com.suyash.currencyconverter.data.local.entity.Rate

data class ApiResponse(
    @SerializedName("timestamp")
    val timestamp: String = "",
    @SerializedName("base")
    val base: String = "",
    @SerializedName("rates")
    val rates: HashMap<String, Float>
)

fun ApiResponse.toRateEntity() : Rate {
    return Rate(
        base = base,
        currencyRates = rates
    )
}

