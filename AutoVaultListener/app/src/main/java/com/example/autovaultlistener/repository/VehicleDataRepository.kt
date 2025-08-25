package com.example.autovaultlistener.repository

import com.example.autovaultlistener.data.dto.VehicleData
import com.example.autovaultlistener.di.RetrofitInstance
import com.google.gson.Gson

class VehicleDataRepository {
    private val api = RetrofitInstance.api
    companion object {
        private const val BIN_ID    = "686e4de477690248e158a209"
        private const val MASTER_KEY = "\$2a\$10\$wSJYtBzJgtzj7Z/vimX9W..0FWtbsJ.eKTdc12JFcq3QmXOlJK0em"
    }
    suspend fun fetchHealthStatus(): VehicleData? {
        val response = api.getVehicleHealthStatus(BIN_ID, MASTER_KEY)
        return if (response.isSuccessful) {
            Gson().fromJson(response.body(), VehicleData::class.java)
        } else null
    }

    suspend fun updateHealthStatus(newStatus: VehicleData): Boolean {
        val response = api.postVehicleHealthStatus(BIN_ID, MASTER_KEY, newStatus)
        return response.isSuccessful
    }
}



