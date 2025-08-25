package com.example.autovault.data.car_api.dto

 class BatteryLevel(
    val propertyId: Int = 0,
    val status: String = "Unknown",
    val unit: String = "percent",
    val value: Int = 0
)
