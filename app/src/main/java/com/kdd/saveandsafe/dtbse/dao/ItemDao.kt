package com.kdd.saveandsafe.dtbse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdd.saveandsafe.dtbse.ety.ItemEntity

@Dao
interface ItemDao {

    @Insert
    suspend fun addItem(etyItem : ItemEntity)

    @Query("SELECT * FROM item_table")
    suspend fun listItem() : List<ItemEntity>

}