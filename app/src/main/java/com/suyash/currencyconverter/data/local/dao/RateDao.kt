package com.suyash.currencyconverter.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.suyash.currencyconverter.data.local.entity.Rate
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao {

    @Query("SELECT * FROM rate")
    fun getAll() : Flow<Rate>

    @Insert
    fun insertAll(rate: Rate)

    @Query("DELETE FROM rate")
    fun deleteAll()

    @Transaction
    fun deleteAllAndInsertAll(rates: Rate) {
        deleteAll()
        return insertAll(rates)
    }
}