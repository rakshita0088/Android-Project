package com.example.autovaultlistener.data.remote

import com.example.autovaultlistener.data.dto.VehicleData
import com.google.gson.JsonElement
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT
import retrofit2.http.Path

interface JSONBinApi {
        @PUT("v3/b/{binId}")
        suspend fun postVehicleHealthStatus(
            @Path("binId") binId: String,
            @Header("X-Master-Key") apiKey: String,
            @Body vehicleHealthStatus: VehicleData
        ): Response<JsonElement>

        @GET("v3/b/{binId}")
        suspend fun getVehicleHealthStatus(
            @Path("binId") binId: String,
            @Header("X-Master-Key") apiKey: String
        ): Response<JsonElement>
}