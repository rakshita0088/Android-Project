package com.example.autovaultlistener.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.autovaultlistener.data.dto.VehicleData
import com.example.autovaultlistener.repository.VehicleDataRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class VehicleViewModel : ViewModel(){
    private val repository = VehicleDataRepository()

    // ✅ Holds the latest vehicle health data
    private val _vehicleHealth = MutableStateFlow<VehicleData?>(null)
    val vehicleHealth: StateFlow<VehicleData?> = _vehicleHealth

    // ✅ Event to trigger heads-up notification in UI
    private val _showNotification = MutableStateFlow(false)
    val showNotification: StateFlow<Boolean> = _showNotification

    // ✅ Fetch vehicle health status from JSONBin
    fun loadHealthStatus() {
        viewModelScope.launch {
            val result = repository.fetchHealthStatus()
            _vehicleHealth.value = result
        }
    }

    // ✅ Update vehicle health and notify UI to show heads-up
    fun updateHealthStatus(newStatus: VehicleData) {
        viewModelScope.launch {
            val success = repository.updateHealthStatus(newStatus)
            if (success) {
                _showNotification.value = true  // 👉 trigger UI to show notification
                loadHealthStatus()              // 👉 refresh UI with new data
            }
        }
    }

    // ✅ Reset notification flag after it has been consumed by UI
    fun resetNotificationFlag() {
        _showNotification.value = false
    }
}