package com.example.autovault.worker
//
////class ReminderWorker {
////}
////package com.example.autovault.worker
//
//import android.Manifest
//import android.annotation.SuppressLint
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import androidx.annotation.RequiresPermission
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.work.CoroutineWorker
//import androidx.work.WorkerParameters
//import com.example.autovault.R
//import com.example.autovault.data.ServiceLogDatabase
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import java.text.SimpleDateFormat
//import java.util.*
//
//class ReminderWorker(ctx: Context, params: WorkerParameters) : CoroutineWorker(ctx, params) {
//    @SuppressLint("MissingPermission")
//    override suspend fun doWork(): Result = withContext(Dispatchers.IO) @androidx.annotation.RequiresPermission(
//        android.Manifest.permission.POST_NOTIFICATIONS
//    ) {
//        val dao = ServiceLogDatabase.getDatabase(applicationContext).dao()
//        val latest = dao.getLatestLog()
//        val today = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
//
//        if (latest?.nextServiceDate == today) sendNotification()
//        Result.success()
//    }
//
//    @RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
//    private fun sendNotification() {
//        val channelId = "service_reminder_channel"
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel = NotificationChannel(channelId, "Service Reminder", NotificationManager.IMPORTANCE_HIGH)
//            val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//            manager.createNotificationChannel(channel)
//        }
//
//        val notification = NotificationCompat.Builder(applicationContext, channelId)
//            .setSmallIcon(android.R.drawable.ic_dialog_info)
//            .setContentTitle("Service Due Today!")
//            .setContentText("Reminder: Your vehicle is due for service today.")
//            .setPriority(NotificationCompat.PRIORITY_HIGH)
//            .build()
//
//        NotificationManagerCompat.from(applicationContext).notify(1, notification)
//    }
//}


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.autovault.R

class ReminderServiceWorker(private val context: Context, params: WorkerParameters) : Worker(context, params) {

    override fun doWork(): Result {
        sendNotification("Vehicle Service Due Today!")
        return Result.success()
    }

    private fun sendNotification(message: String) {
        val channelId = "service_reminder_channel"
        val notificationId = 101

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) return

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Service Reminder")
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)

        val notificationManager = NotificationManagerCompat.from(context)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Service Reminders", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationId, builder.build())
    }
}
 fun sendNotification(message: String, context: Context) {
    val channelId = "service_reminder_channel"
    val notificationId = 101

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
    ) return

    val builder = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentTitle("Service Reminder")
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    val notificationManager = NotificationManagerCompat.from(context)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, "Service Reminders", NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(channel)
    }

    notificationManager.notify(notificationId, builder.build())
}
