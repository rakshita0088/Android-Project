package com.example.autovault.data

//class ServiceLogRepository {
//}
//package com.example.autovault.data

class ServiceLogRepository(private val dao: ServiceLogDao) {
    suspend fun insert(last: String, next: String) =
        dao.insert(ServiceLog(lastServiceDate = last, nextServiceDate = next))

    suspend fun getAllLogs() = dao.getAllLogs()
    suspend fun getLatest() = dao.getLatestLog()
}