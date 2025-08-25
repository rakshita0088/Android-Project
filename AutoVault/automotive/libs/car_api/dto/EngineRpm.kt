package com.project.autobox.data.car_api.dto

import com.project.autobox.common.Constants

data class EngineRpm(
    val propertyId: Int = Constants.ENGINE_RPM_ID,
    val status: String = "unknown",
    val unit: String = "rpm",
    val value: Int = 0
)