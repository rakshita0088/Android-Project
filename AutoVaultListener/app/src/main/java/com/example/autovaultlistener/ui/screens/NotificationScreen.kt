package com.example.autovaultlistener.ui.screens

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import com.example.autovaultlistener.data.dto.BatteryLevel
import com.example.autovaultlistener.data.dto.VehicleData
import com.example.autovaultlistener.ui.viewmodel.VehicleViewModel

@Composable
fun VehicleHealthScreen(viewModel: VehicleViewModel) {
    val context = LocalContext.current
    val healthStatus by viewModel.vehicleHealth.collectAsState()
    val showNotification by viewModel.showNotification.collectAsState()

    // Load data once on screen entry
    LaunchedEffect(Unit) {
        viewModel.loadHealthStatus()
    }

    // Show heads-up notification if ViewModel tells us to
    LaunchedEffect(showNotification) {
        if (showNotification) {
            showHeadsUpNotification(context)
            viewModel.resetNotificationFlag()
        }
    }

    // UI Layout
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Vehicle Health", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        healthStatus?.let { status ->
            Text("🔋 Battery: ${status.batteryLevel.value}%")
        } ?: Text("Loading health status...")

        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = {
            // Update with sample values
            viewModel.updateHealthStatus(
                VehicleData(
                    batteryLevel = BatteryLevel(
                        value = 30
                    )
                )
            )
        }) {
            Text("Update Status")
        }
    }
}

fun showHeadsUpNotification(context: Context) {
    val channelId = "vehicle_health_channel"
    val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            channelId,
            "Vehicle Health Notifications",
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = "Heads-up alert for vehicle health"
        }
        manager.createNotificationChannel(channel)
    }

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(android.R.drawable.stat_notify_more) // Use your icon
        .setContentTitle("Vehicle Health Synced")
        .setContentText("Your latest vehicle health was updated.")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setDefaults(NotificationCompat.DEFAULT_ALL)
        .setAutoCancel(true)
        .build()

    manager.notify(1001, notification)
}

