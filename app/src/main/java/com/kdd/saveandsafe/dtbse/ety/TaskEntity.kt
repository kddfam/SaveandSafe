package com.kdd.saveandsafe.dtbse.ety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task_table")
data class TaskEntity(
    @ColumnInfo(name = "name")
    var t_name : String,
    @ColumnInfo(name = "time")
    var t_time : String,
    @ColumnInfo(name = "date")
    var t_date : String,
    @ColumnInfo(name = "e_time")
    var t_e_time : String
)
{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var t_id : Int = 0
}