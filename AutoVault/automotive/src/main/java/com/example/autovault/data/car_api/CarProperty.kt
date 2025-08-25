package com.example.autovault.data.car_api

import androidx.annotation.RequiresApi
import android.car.Car
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.Subscription
import android.content.Context
import android.os.Build
import android.util.Log
import java.util.concurrent.Executor

class CarProperty private constructor(
    private val context: Context,
    private val propertyIds: List<Int>,
    private val callback: ((propertyId: Int?, value: Any?) -> Unit)?
){

    private var car: Car? = null
    private var carPropertyManager: CarPropertyManager? = null
    private val carPropertyEventCallback = object : CarPropertyManager.CarPropertyEventCallback{
        override fun onChangeEvent(p0: CarPropertyValue<*>?) {
            callback?.invoke(p0?.propertyId, p0?.value)
        }

        override fun onErrorEvent(p0: Int, p1: Int) {
            Log.e("CarPropertyReader", "Error for $p0 at zone $p1")
        }

    }

//    fun startListening(){
//        car = Car.createCar(context)
//
//        car?.let { carNonNull ->
//            carPropertyManager = carNonNull.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
//
//            try {
//                val subscriptions = propertyIds.map { id ->
//                    Subscription.Builder(id)
//                        .setUpdateRateHz(CarPropertyManager.SENSOR_RATE_ONCHANGE)
//                        .setVariableUpdateRateEnabled(false)
//                        .build()
//
//                }
//
//                val executor: Executor = context.mainExecutor
//
//                carPropertyManager?.subscribePropertyEvents(subscriptions, executor, carPropertyEventCallback)
//            } catch (e: Exception) {
//                Log.e("CarProperty", "Subscription creation failed: ${e.message}", e)
//            }
//        }
//    }

    @RequiresApi(Build.VERSION_CODES.P)
    @Suppress("DEPRECATION") // To suppress the deprecation warning
    fun startListening() {
        car = Car.createCar(context)

        car?.let { carNonNull ->
            carPropertyManager = carNonNull.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
            propertyIds.forEach { id ->
                carPropertyManager?.registerCallback(
                    carPropertyEventCallback,
                    id,
                    CarPropertyManager.SENSOR_RATE_ONCHANGE
                )
            }

//            try {
//                val executor: Executor = context.mainExecutor
//
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
//                    // Use new subscription API (Android 14+)
//                    val subscriptions = propertyIds.map { id ->
//                        Subscription.Builder(id)
//                            .setUpdateRateHz(CarPropertyManager.SENSOR_RATE_ONCHANGE)
//                            .setVariableUpdateRateEnabled(false)
//                            .build()
//                    }
//                    carPropertyManager?.subscribePropertyEvents(subscriptions, executor, carPropertyEventCallback)
//                } else {
//                    // Fallback for Android 13 and below
//
//                }
//
//            } catch (e: Exception) {
//                Log.e("CarProperty", "Subscription creation failed: ${e.message}", e)
//            }
        }
    }


    fun stopListening(){
        try {
//            carPropertyManager?.unsubscribePropertyEvents(carPropertyEventCallback)
//            car?.disconnect()
        }catch (e: Exception){
            Log.e("stopListening", "unsubscribePropertyEvents creation failed: ${e.message}", e)
        }
    }

    class Builder(private val context: Context){
        private val props = mutableListOf<Int>()
        private var cb: ((Int?, Any?) -> Unit)? = null

        fun addProperty(propertyId: Int) = apply { props += propertyId }
        fun setCallBack(callBack: (propertyId: Int?, value: Any?) -> Unit) = apply { cb = callBack }

        fun build(): CarProperty{
            require(cb != null){"you must call setCallBack"}
            return CarProperty(context, props, cb)
        }
    }
}