package com.example.autovault.worker

import android.R
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.autovault.data.car_api.GetVehicleData
import kotlin.compareTo

//import com.example.autovaultlistener.repository.VehicleDataRepository

class VehicleEmergencyCheckWorker(
    private val context: Context,
    workerParams: WorkerParameters
) : CoroutineWorker(context, workerParams){
    private val repository = GetVehicleData(context)

    @RequiresApi(Build.VERSION_CODES.P)
    override suspend fun doWork(): Result {
        return try {
            val health = repository.subscribeVehicleProperties()
//            val viewModel = Qui
            health.let {
                if (it.batteryLevel.value < 20) {
                    showBatteryLowNotification(context, it.batteryLevel.value)
                }
                if (it.flatTyre.value < 40) {
                    showFlatTyreNotification(context, it.flatTyre.value)
                }

                if (it.brakeProblem.value < 85) {
                    showBrakeProblemNotification(context, it.brakeProblem.value)
                }

                if (it.headlightFailure.value < 120 ) {
                    showHeadlightFailureNotification(context, it.headlightFailure.value)
                }

                if (it.overheatingEngine.value > 90) {
                    showEngineOverheatNotification(context, it.overheatingEngine.value)
                }

            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showBatteryLowNotification(context: Context, batteryLevel: Int) {
        val channelId = "vehicle_battery_alerts"
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Vehicle Battery Alerts",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Battery alerts from vehicle health"
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_dialog_alert)
            .setContentTitle("Battery Low!")
            .setContentText("Battery is at $batteryLevel%. Please check vehicle.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .build()

        manager.notify(2001, notification)
    }
    private fun showFlatTyreNotification(context: Context, flatTyre: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "flat_tyre_alert"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Flat Tyre Alerts", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Flat Tyre Detected!")
            .setContentText("Please check your tyres immediately.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager.notify(1001, notification)
    }

    private fun showBrakeProblemNotification(context: Context, breakProblems: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "brake_alert"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Brake Issue Alerts", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Brake Issue Detected!")
            .setContentText("Brake system might be failing. Please check immediately.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager.notify(1002, notification)
    }

    private fun showHeadlightFailureNotification(context: Context, headlightFailure: Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "headlight_alert"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Headlight Failure Alerts", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Headlight Failure!")
            .setContentText("Headlights are not working. Drive cautiously.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager.notify(1003, notification)
    }

    private fun showEngineOverheatNotification(context: Context, overheatingEngine:  Int) {
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "engine_temp_alert"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Overheating Engine Alerts", NotificationManager.IMPORTANCE_HIGH)
            manager.createNotificationChannel(channel)
        }
        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_alert)
            .setContentTitle("Engine Overheating!")
            .setContentText("Engine temperature is ${overheatingEngine}°C. Stop and cool down the engine.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
        manager.notify(1004, notification)
    }

}