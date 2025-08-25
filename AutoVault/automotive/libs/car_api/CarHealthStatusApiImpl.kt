package com.project.autobox.data.car_api

import android.content.Context
import android.util.Log
import com.project.autobox.common.Constants
import com.project.autobox.data.car_api.dto.BatteryLevel
import com.project.autobox.data.car_api.dto.EngineCoolantTemp
import com.project.autobox.data.car_api.dto.EngineOilLevel
import com.project.autobox.data.car_api.dto.EngineRpm
import com.project.autobox.data.car_api.dto.VehicleHealthStatus
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume

class   CarHealthStatusApiImpl(private val context: Context
): CarHealthStatusApi {

    private lateinit var batteryLevel: BatteryLevel

    override suspend fun getCarHealthStatus(): VehicleHealthStatus =
        withTimeoutOrNull(5000) {
            suspendCancellableCoroutine { cont ->
                var received = false
                lateinit var  carProperty: CarProperty
                carProperty = CarProperty.Builder(context)
                    .addProperty(Constants.FUEL_LEVEL_ID)
                    .setCallBack { propertyId, value ->
                        if (propertyId == Constants.FUEL_LEVEL_ID && value is Int) {
                            val batteryLevel = BatteryLevel(
                                propertyId = Constants.FUEL_LEVEL_ID,
                                status = "OK",
                                value = value,
                                unit = "%"
                            )

                            if (!received && cont.isActive) {
                                received = true

                                try {
                                    cont.resume(VehicleHealthStatus(batteryLevel))
                                }catch (e: Exception) {
                                    Log.e("CarProperty", "Subscription creation failed: ${e.message}", e)
                                }
                                carProperty.stopListening()
                            }
                        }
                    }
                    .build()

                cont.invokeOnCancellation {
                    carProperty.stopListening()
                }

                carProperty.startListening()
            }
        } ?: VehicleHealthStatus(
            batteryLevel = BatteryLevel(
                propertyId = Constants.BATTERY_ID,
                status = "Unknown",
                value = 10,
                unit = "%"
            ),
            engineOilLevel = EngineOilLevel(
                propertyId = Constants.ENGINE_OIL_LEVEL_ID,
                value = 70
            ),
            engineRpm = EngineRpm(
                value = 6000
            ),
            engineCoolantTemp = EngineCoolantTemp(
                value = 50,
                unit = "C"

            )
        )

}