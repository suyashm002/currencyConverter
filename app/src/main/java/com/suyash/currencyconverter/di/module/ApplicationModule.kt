package com.suyash.currencyconverter.di.module

import android.content.Context
import androidx.room.Room
import com.suyash.currencyconverter.data.api.NetworkService
import com.suyash.currencyconverter.data.local.AppDatabase
import com.suyash.currencyconverter.data.local.AppDatabaseService
import com.suyash.currencyconverter.data.local.AppSharedPreferenceService
import com.suyash.currencyconverter.data.local.DatabaseService
import com.suyash.currencyconverter.data.local.SharedPreferenceService
import com.suyash.currencyconverter.utils.DefaultNetworkHelper
import com.suyash.currencyconverter.utils.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @BaseUrl
    @Provides
    fun provideBaseUrl(): String = "https://openexchangerates.org/api/"

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    @Provides
    @Singleton
    fun provideNetworkService(
        @BaseUrl baseUrl: String,
        gsonConverterFactory: GsonConverterFactory
    ): NetworkService {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(gsonConverterFactory)
            .build()
            .create(NetworkService:: class.java)
    }

    @Provides
    @Singleton
    fun provideNetworkHelper(@ApplicationContext context: Context) : NetworkHelper {
        return DefaultNetworkHelper(context)
    }

    @DatabaseName
    @Provides
    fun provideDatabaseName(): String = "currency-database"

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        @DatabaseName databaseName: String
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            databaseName
        ).build()
    }

    @Provides
    @Singleton
    fun provideDatabaseService(appDatabase: AppDatabase) : DatabaseService {
        return AppDatabaseService(appDatabase)
    }

    @Provides
    @Singleton
    fun provideSharedPrefernce(
    @ApplicationContext context : Context
    ): SharedPreferenceService {
       return AppSharedPreferenceService(context)
    }

}