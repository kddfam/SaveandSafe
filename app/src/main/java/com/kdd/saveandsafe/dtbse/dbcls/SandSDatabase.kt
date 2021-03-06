package com.kdd.saveandsafe.dtbse.dbcls

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kdd.saveandsafe.dtbse.dao.ItemDao
import com.kdd.saveandsafe.dtbse.dao.PriceDao
import com.kdd.saveandsafe.dtbse.dao.TaskDao
import com.kdd.saveandsafe.dtbse.ety.ItemEntity
import com.kdd.saveandsafe.dtbse.ety.PriceEntity
import com.kdd.saveandsafe.dtbse.ety.TaskEntity

@Database(
    entities = [PriceEntity::class, ItemEntity::class, TaskEntity::class],
    version = 6,
    exportSchema = false
)
abstract class SandSDatabase : RoomDatabase() {

    abstract fun getPriceDao() : PriceDao
    abstract fun getItemDao() : ItemDao
    abstract fun getTaskDao() : TaskDao

    companion object {

        @Volatile
        private var instance : SandSDatabase? = null
        private val LOCK = Any()

        operator fun invoke(mContext: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(mContext).also {
                instance = it
            }
        }

        private fun buildDatabase(mContext: Context) = Room.databaseBuilder(mContext.applicationContext, SandSDatabase::class.java, "sands")
            .fallbackToDestructiveMigration()
            .build()
    }
}