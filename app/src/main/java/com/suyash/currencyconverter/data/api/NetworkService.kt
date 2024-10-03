package com.suyash.currencyconverter.data.api

import com.suyash.currencyconverter.data.model.ApiResponse
import com.suyash.currencyconverter.utils.AppConstant.API_KEY
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("latest.json")
    suspend fun getCurrencies(@Query("app_id", encoded = true) apiKey: String): ApiResponse
}