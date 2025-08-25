package com.example.autovaultlistener.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager

fun VehicleEmergencyCheckScheduler(context: Context) {
    val workRequest = PeriodicWorkRequestBuilder<VehicleEmergencyCheckWorker>(
        15, java.util.concurrent.TimeUnit.MINUTES
    ).build()

    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
    "vehicle_health_worker",
    ExistingPeriodicWorkPolicy.KEEP,
    workRequest
    )
}
//    val workRequest = OneTimeWorkRequestBuilder<VehicleEmergencyCheckWorker>().build()
//    WorkManager.getInstance(context).enqueue(workRequest)
//}