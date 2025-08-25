package com.example.autovault.ViewModel
//
////class ServiceReminderViewModel {
////}
////package com.example.autovault.viewmodel
//
//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//import androidx.lifecycle.viewModelScope
//import com.example.autovault.data.ServiceLogDatabase
//import com.example.autovault.data.ServiceLogRepository
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.asStateFlow
//import kotlinx.coroutines.launch
//
//class ServiceReminderViewModel(app: Application) : AndroidViewModel(app) {
//    private val repo = ServiceLogRepository(ServiceLogDatabase.getDatabase(app).dao())
//
//    private val _logs = MutableStateFlow(emptyList<com.example.autovault.data.ServiceLog>())
//    val logs = _logs.asStateFlow()
//
//    fun saveLog(last: String, next: String) {
//        viewModelScope.launch {
//            repo.insert(last, next)
//            loadLogs()
//        }
//    }
//
//    fun loadLogs() {
//        viewModelScope.launch {
//            _logs.value = repo.getAllLogs()
//        }
//    }
//
//    suspend fun getNextServiceDate(): String? = repo.getLatest()?.nextServiceDate
//}


//import android.app.Application
//import androidx.lifecycle.AndroidViewModel
//
//class ServiceReminderViewModel(application: Application) : AndroidViewModel(application) {
//
//    private val serviceLogs = mutableListOf<String>()
//
//    fun saveServiceLog(lastDate: String, nextDate: String) {
//        serviceLogs.add("Last: $lastDate | Next: $nextDate")
//    }
//
//    fun getServiceLogs(): List<String> {
//        return serviceLogs
//    }
//}


// ServiceReminderViewModel.kt


import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class ServiceReminderViewModel : ViewModel() {
    private val _reminders = MutableStateFlow<List<String>>(emptyList())
    val reminders: StateFlow<List<String>> = _reminders

    fun addReminder(date: String) {
        _reminders.value = _reminders.value + date
    }
}
