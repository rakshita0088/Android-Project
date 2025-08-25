package com.project.autobox.data.car_api.dto

data class BatteryVoltage(
    val propertyId: String,
    val status: String,
    val unit: String,
    val value: Double
)