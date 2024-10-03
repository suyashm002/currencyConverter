package com.suyash.currencyconverter.data.local

interface SharedPreferenceService {

    fun  getTimeStamp() : Boolean

    fun saveTimeStamp()
}