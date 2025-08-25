package com.example.autovault.data

//class ServiceLogDao {
//}
//package com.example.autovault.data

import androidx.room.*

@Dao
interface ServiceLogDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(log: ServiceLog)

    @Query("SELECT * FROM service_log ORDER BY id DESC")
    suspend fun getAllLogs(): List<ServiceLog>

    @Query("SELECT * FROM service_log ORDER BY id DESC LIMIT 1")
    suspend fun getLatestLog(): ServiceLog?
}