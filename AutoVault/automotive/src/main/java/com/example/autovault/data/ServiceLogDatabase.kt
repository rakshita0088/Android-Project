package com.example.autovault.data

//class ServiceLogDatabase {
//}
//package com.example.autovault.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ServiceLog::class], version = 1)
abstract class ServiceLogDatabase : RoomDatabase() {
    abstract fun dao(): ServiceLogDao

    companion object {
        @Volatile private var INSTANCE: ServiceLogDatabase? = null
        fun getDatabase(context: Context): ServiceLogDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ServiceLogDatabase::class.java,
                    "service_log_db"
                ).build().also { INSTANCE = it }
            }
    }
}