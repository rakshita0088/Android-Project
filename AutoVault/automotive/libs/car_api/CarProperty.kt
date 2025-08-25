package com.project.autobox.data.car_api

import android.car.Car
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.Subscription
import android.content.Context
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

    fun startListening(){
        car = Car.createCar(context)

        car?.let { carNonNull ->
            carPropertyManager = carNonNull.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager

            try {
                val subscriptions = propertyIds.map { id ->
                    val allConfigs = carPropertyManager?.propertyList
                    allConfigs?.forEach {
                        Log.d("CarProperty", "Supported Property ID: ${it.propertyId}")
                    }
                    Subscription.Builder(id)
                        .setUpdateRateHz(CarPropertyManager.SENSOR_RATE_ONCHANGE)
                        .setVariableUpdateRateEnabled(false)
                        .build()
                }

                val executor: Executor = context.mainExecutor

                carPropertyManager?.subscribePropertyEvents(subscriptions, executor, carPropertyEventCallback)
            } catch (e: Exception) {
                Log.e("CarProperty", "Subscription creation failed: ${e.message}", e)
            }
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