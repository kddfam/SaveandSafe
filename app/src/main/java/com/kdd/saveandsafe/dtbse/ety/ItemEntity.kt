package com.kdd.saveandsafe.dtbse.ety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item_table")
data class ItemEntity(
    @ColumnInfo(name = "name")
    var i_name : String,
    @ColumnInfo(name = "price")
    var i_price : Int,
    @ColumnInfo(name = "time")
    var i_time : String,
    @ColumnInfo(name = "date")
    var i_date : String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var i_id : Int = 0
}