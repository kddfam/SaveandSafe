package com.kdd.saveandsafe.dtbse.ety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "price_table")
data class PriceEntity (
    @ColumnInfo(name = "amount")
    var p_amount : Int,
    @ColumnInfo(name = "updated_amount")
    var p_updated_amount : Int,
    @ColumnInfo(name = "savings")
    var p_savings : Int,
    @ColumnInfo(name = "total_items")
    var p_total_items : Int,
    @ColumnInfo(name = "time")
    var p_time : String,
    @ColumnInfo(name = "date")
    var p_date : String
) {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var p_id : Int = 0
}