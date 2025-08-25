package com.project.autobox.data.car_api

import com.project.autobox.data.car_api.dto.VehicleHealthStatus

interface CarHealthStatusApi {

    suspend fun getCarHealthStatus(): VehicleHealthStatus
}