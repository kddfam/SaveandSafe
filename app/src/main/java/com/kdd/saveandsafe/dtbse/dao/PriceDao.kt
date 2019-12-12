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

    @Query("SELECT * FROM (SELECT * FROM price_table ORDER BY id DESC LIMIT 3) AS R ORDER BY id")
    suspend fun listLastThree() : List<PriceEntity>

    @Query("SELECT * FROM (SELECT * FROM price_table ORDER BY id DESC LIMIT 1) AS ONE ORDER BY id")
    suspend fun listRecent() : List<PriceEntity>

    @Query("UPDATE price_table SET updated_amount = :updated_amount,total_items = :total_items WHERE id = :id")
    suspend fun updateAmount(updated_amount : Int, total_items : Int, id : Int)
}