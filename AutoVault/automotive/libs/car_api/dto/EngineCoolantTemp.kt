package com.project.autobox.data.car_api.dto

import com.project.autobox.common.Constants

data class EngineCoolantTemp(
    val propertyId: Int = Constants.ENGINE_COOLANT_TEMP_ID,
    val status: String = "Unknown",
    val unit: String = " °C",
    val value: Int = 0
)