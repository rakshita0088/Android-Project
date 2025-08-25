package com.example.autovault.network

//class SOSapi {
//}
//package com.example.autovault.network

//import retrofit2.Call
//import retrofit2.http.Body
//import retrofit2.http.Headers
//import retrofit2.http.POST
//
//data class SOSRequest(
//    val message: String = "Emergency!! Help Me!",
//    val latitude: Double = 0.0,
//    val longitude: Double = 0.0,
//    val contactNumber: String = "8075274706"
//)
//
//interface SOSApi {
//    @Headers("Content-Type: application/json")
//    @POST("your-api-endpoint") // e.g., "https://api.jsonbin.io/v3/b/YOUR_ID"
//    fun sendSOS(@Body request: SOSRequest): Call<Void>
//}
//package com.example.autovault.network

import com.example.autovault.data.car_api.dto.VehicleData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.PUT

interface SOSApi {
    @PUT("v3/b/686e4de477690248e158a209")  // Replace with actual ID
    @Headers(
        "X-Master-Key: \$2a\$10\$wSJYtBzJgtzj7Z/vimX9W..0FWtbsJ.eKTdc12JFcq3QmXOlJK0em",
        "Content-Type: application/json"
    )

    fun sendSOS(@Body data: VehicleData): Call<Void>
}
