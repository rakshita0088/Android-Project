package com.example.autovault.worker

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

//fun ReminderServiceScheduler(context: Context) {
//    val workRequest = PeriodicWorkRequestBuilder<ReminderServiceWorker>(
//        15, TimeUnit.MINUTES
//    ).build()
//
//    WorkManager.getInstance(context).enqueueUniquePeriodicWork(
//    "ReminderServiceScheduler",
//    ExistingPeriodicWorkPolicy.KEEP,
//    workRequest
//    )
//}

fun ReminderServiceScheduler(context: Context) {
    val oneTimeWorkRequest = OneTimeWorkRequestBuilder<ReminderServiceWorker>()
        .build()

    WorkManager.getInstance(context).enqueue(oneTimeWorkRequest)
}