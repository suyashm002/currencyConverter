package com.suyash.currencyconverter.data.local

interface SharedPreferenceService {

    fun  isMoreThanThirtyMinutes() : Boolean

    fun saveTimeStamp()
}