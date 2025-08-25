package com.example.autovault.ViewModel

import com.example.autovault.data.car_api.dto.VehicleData

data class ViewModelState(
    val isLoading: Boolean = false,
    val status: VehicleData? = null,
    val error: String = ""
)
