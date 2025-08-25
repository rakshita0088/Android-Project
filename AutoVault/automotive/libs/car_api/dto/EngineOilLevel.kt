package com.project.autobox.data.car_api.dto

import com.project.autobox.common.Constants

data class EngineOilLevel(
    val propertyId: Int = Constants.ENGINE_OIL_LEVEL_ID,
    val status: String = "Unknown",
    val unit: String = "%",
    val value: Int = 0
)