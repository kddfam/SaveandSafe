package com.kdd.saveandsafe.dtbse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdd.saveandsafe.dtbse.ety.PriceEntity

@Dao
interface PriceDao {

    @Insert
    suspend fun addPrice(etyPrice : PriceEntity)

    @Query("SELECT * FROM price_table ORDER BY id DESC")
    suspend fun listPrice() : List<PriceEntity>

    @Query("SELECT * FROM (SELECT * FROM price_table ORDER BY id DESC LIMIT 5) AS R ORDER BY id")
    suspend fun listLastFive() : List<PriceEntity>
}