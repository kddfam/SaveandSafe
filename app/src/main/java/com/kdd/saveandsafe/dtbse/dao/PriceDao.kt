package com.kdd.saveandsafe.dtbse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdd.saveandsafe.dtbse.ety.PriceEntity

@Dao
interface PriceDao {

    @Insert
    suspend fun addPrice(etyPrice : PriceEntity)

    @Query("SELECT * FROM price_table")
    suspend fun listPrice() : List<PriceEntity>

}