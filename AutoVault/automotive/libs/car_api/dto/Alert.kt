package com.project.autobox.data.car_api.dto

data class Alert(
    val id: String,
    val message: String,
    val propertyId: String,
    val severity: String,
    val timestamp: String
)