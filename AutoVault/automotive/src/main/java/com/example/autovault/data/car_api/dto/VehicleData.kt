package com.example.autovault.data.car_api.dto

data class  VehicleData (
    val batteryLevel: BatteryLevel = BatteryLevel(),
    val flatTyre: FlatTyre = FlatTyre(),
    val overheatingEngine: OverheatingEngine = OverheatingEngine(),
    val brakeProblem: BrakeProblems = BrakeProblems(),
    val headlightFailure: HeadlightFailure = HeadlightFailure(),
    var ignitionStatus: IgnitionStatus = IgnitionStatus(),
//    val emergencyAlert: EmergencyAlert = EmergencyAlert()

    val emergencyAlert: EmergencyAlert = EmergencyAlert(
        message = "Emergency!! Help Me!!",
        longitude = 12.9110,
        latitude = 77.5796,
        contactNumber = "8446309202"
    )
)
