package com.project.autobox.data.car_api.dto

data class VehicleHealthStatus(
    val batteryLevel: BatteryLevel = BatteryLevel(),
    val engineOilLevel: EngineOilLevel = EngineOilLevel(),
    val engineRpm: EngineRpm = EngineRpm(),
    val engineCoolantTemp: EngineCoolantTemp = EngineCoolantTemp(),
)