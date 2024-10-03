package com.suyash.currencyconverter.data.model

import com.google.gson.annotations.SerializedName
import com.suyash.currencyconverter.data.local.entity.Rate

data class CurrencyRates(
    @SerializedName("AED") var AED : String,
    @SerializedName("AFN") var AFN : String,
    @SerializedName("ALL") var ALL : String,
    @SerializedName("AMD") var AMD : String,
    @SerializedName("ANG") var ANG : String,
    @SerializedName("AOA") var AOA : String,
    @SerializedName("ARS") var ARS : String,
    @SerializedName("AUD") var AUD : String
)

//fun CurrencyRates.toRateEntity() : Rate {
//    return Rate(
////        AED = AED,
////        AFN = AFN,
////        ALL = ALL,
////        AMD = AMD,
////        ANG = ANG,
////        AOA = AOA,
////        ARS = ARS,
////        AUD = AUD
//    )
//}
