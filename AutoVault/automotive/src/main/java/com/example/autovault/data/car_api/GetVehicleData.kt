package com.example.autovault.data.car_api


import android.car.VehiclePropertyIds
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.autovault.data.car_api.dto.BatteryLevel
import com.example.autovault.data.car_api.dto.BrakeProblems
import com.example.autovault.data.car_api.dto.FlatTyre
import com.example.autovault.data.car_api.dto.HeadlightFailure
import com.example.autovault.data.car_api.dto.IgnitionStatus
import com.example.autovault.data.car_api.dto.OverheatingEngine
import com.example.autovault.data.car_api.dto.VehicleData
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.coroutines.resume


class GetVehicleData(private val context: Context
) {

    private lateinit var batteryLevel: BatteryLevel

    @RequiresApi(Build.VERSION_CODES.P)
    suspend fun subscribeVehicleProperties(): VehicleData =
        withTimeoutOrNull(5000) {
            suspendCancellableCoroutine { cont ->
                var received = false
                lateinit var  carProperty: CarProperty
                carProperty = CarProperty.Builder(context)
                    .addProperty(VehiclePropertyIds.EV_BATTERY_LEVEL)
                    .addProperty((VehiclePropertyIds.DOOR_POS))
                    .setCallBack { propertyId, value ->
                        if (propertyId == VehiclePropertyIds.EV_BATTERY_LEVEL && value is Int) {
                            val batteryLevel = BatteryLevel(
                                propertyId = VehiclePropertyIds.EV_BATTERY_LEVEL,
                                status = "OK",
                                value = value,
                                unit = "%"
                            )

                            if (!received && cont.isActive) {
                                received = true

                                try {
                                    cont.resume(VehicleData(batteryLevel))
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
        } ?: VehicleData(
            batteryLevel = BatteryLevel(
                propertyId = VehiclePropertyIds.EV_BATTERY_LEVEL,
                status = "Unknown",
                value = 10,
                unit = "%"
            ),
            flatTyre = FlatTyre(
                value = 35,
                unit = "pascal"
            ),
            overheatingEngine = OverheatingEngine(
                status = "Critical",
                unit = "°C",
                value = 120
            ),
            brakeProblem = BrakeProblems(
                status = "Worn",
                unit = "%",
                value = 75
            ),
            headlightFailure = HeadlightFailure(
                status = "Failed",
                unit = "%",
                value = 100
            )
        )
    @RequiresApi(Build.VERSION_CODES.P)
    fun fetchData(): Flow<VehicleData> = callbackFlow {
        val currentStatus = VehicleData() // You may use a mutable builder if needed

        val carProperty = CarProperty.Builder(context)
            .addProperty(VehiclePropertyIds.PARKING_BRAKE_ON)
            .addProperty(VehiclePropertyIds.GEAR_SELECTION)
            .addProperty(VehiclePropertyIds.HVAC_FAN_SPEED)
            .addProperty(VehiclePropertyIds.HVAC_TEMPERATURE_SET)
            .addProperty(VehiclePropertyIds.ENV_OUTSIDE_TEMPERATURE)
            .addProperty(VehiclePropertyIds.IGNITION_STATE)
            .addProperty(VehiclePropertyIds.NIGHT_MODE)
            .setCallBack { propertyId, value ->
                when (propertyId) {
//                    VehiclePropertyIds.PARKING_BRAKE_ON -> {
//                        currentStatus.parkingBrakeStatus = ParkingBrakeStatus(
//                            propertyId = propertyId,
//                            status = "OK",
//                            value = (value as? Boolean) ?: false ,
//                            unit = "ON/OFF"
//                        )
//                    }

//                    VehiclePropertyIds.GEAR_SELECTION -> {
//                        currentStatus.gearStatus = GearStatus(
//                            propertyId,
//                            status = "OK",
//                            value = value as? Int ?: 0,
//                            unit = "GEAR"
//                        )
//                    }

//                    VehiclePropertyIds.HVAC_FAN_SPEED -> {
//                        currentStatus.fanSpeedStatus = HvacFanSpeedStatus(
//                            propertyId,
//                            "OK",
//                            value as? Int ?: 0,
//                            "LEVEL"
//                        )
//                    }

//                    VehiclePropertyIds.HVAC_TEMPERATURE_SET -> {
//                        currentStatus.temperatureStatus = HvacTemperatureStatus(
//                            propertyId,
//                            "OK",
//                            (value as? Float)?.toDouble() ?: 0.0,
//                            "°C"
//                        )
//                    }

//                    VehiclePropertyIds.ENV_OUTSIDE_TEMPERATURE -> {
//                        currentStatus.outsideTempStatus = OutsideTempStatus(
//                            propertyId,
//                            "OK",
//                            (value as? Float)?.toDouble() ?: 0.0,
//                            "°C"
//                        )
//                    }

                    VehiclePropertyIds.IGNITION_STATE -> {
                        currentStatus.ignitionStatus = IgnitionStatus(
                            propertyId,
                            status = "OK",
                            value = value as? Int ?: 0,
                            unit = "STATE"
                        )
                    }

//                    VehiclePropertyIds.NIGHT_MODE -> {
//                        currentStatus.nightModeStatus = NightModeStatus(
//                            propertyId,
//                            status = "OK",
//                            value = value as? Boolean ?: false,
//                            unit = "ON/OFF"
//                        )
//                    }
                }


                trySend(currentStatus.copy())
            }
            .build()

        carProperty.startListening()

        awaitClose {
            carProperty.stopListening()
        }
    }
}