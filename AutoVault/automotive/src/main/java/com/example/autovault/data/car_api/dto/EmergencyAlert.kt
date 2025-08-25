package com.example.autovault.data.car_api.dto

//
//class EmergencyAlert(
//    val message: String = "Emergency!! Help Me!!",
//    val longitude: Double = 0.0,
//    val latitude: Double = 0.0,
//    val contactNumber: String = "8446309202",
//    var isButtonClicked: String = "Yes"
//)
data class EmergencyAlert(
    val latitude: Double,
    val longitude: Double,
    val message: String,
    val contactNumber: String
)
