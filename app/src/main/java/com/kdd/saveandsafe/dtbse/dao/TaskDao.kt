package com.kdd.saveandsafe.dtbse.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kdd.saveandsafe.dtbse.ety.TaskEntity

@Dao
interface TaskDao {

    @Insert
    suspend fun addTask(etyTask : TaskEntity)

    @Query("SELECT * FROM task_table ORDER BY id DESC")
    suspend fun listTask() : List<TaskEntity>
}