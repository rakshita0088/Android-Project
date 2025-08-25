package com.example.autovault.data

//class ServiceLog {
//}


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "service_log")
data class ServiceLog(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val lastServiceDate: String,
    val nextServiceDate: String
)