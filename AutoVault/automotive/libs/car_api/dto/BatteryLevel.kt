package com.project.autobox.data.car_api.dto

data class BatteryLevel(
    val propertyId: Int = 0,
    val status: String = "Unknown",
    val unit: String = "percent",
    val value: Int = 0
)