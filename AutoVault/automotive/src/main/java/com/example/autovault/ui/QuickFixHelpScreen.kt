package com.example.autovault.ui

import android.car.VehiclePropertyIds
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.autovault.data.car_api.GetVehicleData
import com.example.autovault.data.car_api.dto.BatteryLevel
import com.example.autovault.data.car_api.dto.VehicleData


@Composable
fun     QuickFixHelpScreen(navController: NavController,
                       vehicleData: VehicleData) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(quickFixItems) { item ->

            when(item.title){
                "Battery Issues" -> {
                    ExpandableQuickFixCard(item, vehicleData, "Battery Issues")
                }
                "Flat Tyre" ->{
                    ExpandableQuickFixCard(item, vehicleData, "Flat Tyre")
                }
                "Overheating Engine" -> {
                    ExpandableQuickFixCard(item, vehicleData, "Overheating Engine")
                }
                "Brake Problems" -> {
                    ExpandableQuickFixCard(item, vehicleData, "Brake Problems")
                }
                "Headlight Failure" -> {
                    ExpandableQuickFixCard(item, vehicleData, "Headlight Failure")
                }
                "Ignition Status" -> {
                    ExpandableQuickFixCard(item, vehicleData, "Ignition Status")
                }

            }


        }
    }
}

// ✅ Expandable Card
@Composable
fun ExpandableQuickFixCard(item: QuickFixItem, vehicleData: VehicleData, msg: String) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.weight(1f)
                )
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Info",
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))

                when(msg){
                    "Battery Issues" ->{
                        Text(
                            text = "$msg ${vehicleData.batteryLevel.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    "Flat Tyre" -> {
                        Text(
                            text = "$msg ${vehicleData.flatTyre.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    "Overheating Engine" -> {
                        Text(
                            text = "$msg ${vehicleData.overheatingEngine.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    "Brake Problems" -> {
                        Text(
                            text = "$msg ${vehicleData.brakeProblem.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    "Headlight Failure" -> {
                        Text(
                            text = "$msg ${vehicleData.headlightFailure.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    "Ignition Status" -> {
                        Text(
                            text = "$msg ${vehicleData.ignitionStatus.value}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    item.steps.forEach { step ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(vertical = 4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Info,
                                contentDescription = "Bullet",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = step,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

// ✅ Model class
data class QuickFixItem(
    val title: String,
    val steps: List<String>
)

// ✅ Data
val quickFixItems = listOf(
    QuickFixItem(
        "Battery Issues",
        listOf(
            "Check for corroded terminals",
            "Ensure the battery is charged",
            "Inspect battery cables for damage",
            "Try jump-starting if dead"
        )
    ),
    QuickFixItem(
        "Flat Tyre",
        listOf(
            "Check tyre pressure using a gauge",
            "Look for nails or punctures",
            "Use spare tyre or tyre inflator",
            "Ensure jack and wrench are available"
        )
    ),
    QuickFixItem(
        "Overheating Engine",
        listOf(
            "Turn off AC and pull over safely",
            "Let the engine cool for 15–30 minutes",
            "Check coolant level",
            "Inspect for radiator leaks"
        )
    ),
    QuickFixItem(
        "Engine Won’t Start",
        listOf(
            "Ensure gear is in Park or Neutral",
            "Check if battery is dead",
            "Listen for clicking sound (starter issue)",
            "Try jump-starting vehicle"
        )
    ),
    QuickFixItem(
        "Brake Problems",
        listOf(
            "Listen for squealing or grinding noises",
            "Check brake fluid level",
            "Avoid driving if pedal feels soft",
            "Get brakes inspected immediately"
        )
    ),
    QuickFixItem(
        "Check Engine Light",
        listOf(
            "Ensure fuel cap is properly closed",
            "Use an OBD scanner if available",
            "Avoid driving if light is blinking",
            "Visit mechanic if light stays on"
        )
    ),
    QuickFixItem(
        "Headlight Failure",
        listOf(
            "Check for blown fuses in fuse box",
            "Replace burnt-out bulbs",
            "Use hazard lights if visibility is low",
            "Get replacement bulbs from nearby shop"
        )
    ),
    QuickFixItem(
        "Steering Issues",
        listOf(
            "Check power steering fluid level",
            "Avoid sharp turns if steering feels stiff",
            "Check tyre pressure on all wheels",
            "If pulling to one side, visit a mechanic"
        )
    ),
    QuickFixItem(
        "Ignition Status",
        listOf(
            "Check if the battery is charged and properly connected",
            "Ensure the key fob is detected (for push-to-start vehicles)",
            "Inspect ignition fuse and replace if blown",
            "Try turning the key/push-button again after waiting a few seconds",
            "Listen for clicking or cranking sounds to diagnose starter issues",
            "If the engine doesn't crank, have the starter motor inspected by a mechanic"
        )
    )
)


//import android.Manifest
//import android.R
//import android.app.NotificationChannel
//import android.app.NotificationManager
//import android.content.Context
//import android.os.Build
//import androidx.annotation.RequiresPermission
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Info
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.unit.dp
//import androidx.core.app.NotificationCompat
//import androidx.core.app.NotificationManagerCompat
//import androidx.navigation.NavController
//import com.example.autovault.data.car_api.dto.VehicleData
//
//
//// 🔔 Notification constants
//private const val CHANNEL_ID = "ignition_status_warning_channel"
//private const val NOTIFICATION_ID = 101
//
//// ✅ Composable with NavController for navigation
//@Composable
//fun QuickFixHelpScreen(
//    navController: NavController,
//    vehicleData: VehicleData
//) {
//    val context = LocalContext.current
//    var lastWarnedState by remember { mutableStateOf<String?>(null) }
//
//    // 🔔 Create channel once
//    LaunchedEffect(Unit) {
//        createNotificationChannel(context)
//    }
//
//    // 👁️ Observe ignition status and send warning if needed
//    LaunchedEffect(vehicleData.ignitionStatus.value) {
//        val state = vehicleData.ignitionStatus.value?.uppercase() ?: ""
//        if (state in listOf("ACC", "OFF", "LOCK") && lastWarnedState != state) {
//            showIgnitionWarning(context, state)
//            lastWarnedState = state
//        }
//    }
//
//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(16.dp)
//    ) {
//        items(quickFixItems) { item ->
//            when (item.title) {
//                "Battery Issues" -> ExpandableQuickFixCard(item, vehicleData, "Battery Issues")
//                "Flat Tyre" -> ExpandableQuickFixCard(item, vehicleData, "Flat Tyre")
//                "Overheating Engine" -> ExpandableQuickFixCard(item, vehicleData, "Overheating Engine")
//                "Brake Problems" -> ExpandableQuickFixCard(item, vehicleData, "Brake Problems")
//                "Headlight Failure" -> ExpandableQuickFixCard(item, vehicleData, "Headlight Failure")
//                "Ignition Status" -> ExpandableQuickFixCard(item, vehicleData, "Ignition Status")
//            }
//        }
//    }
//}
//
//// ✅ Expandable Card
//@Composable
//fun ExpandableQuickFixCard(item: QuickFixItem, vehicleData: VehicleData, msg: String) {
//    var expanded by remember { mutableStateOf(false) }
//
//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .clickable { expanded = !expanded },
//        colors = CardDefaults.cardColors(
//            containerColor = MaterialTheme.colorScheme.surfaceVariant
//        ),
//        elevation = CardDefaults.cardElevation(4.dp)
//    ) {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Row(verticalAlignment = Alignment.CenterVertically) {
//                Text(
//                    text = item.title,
//                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
//                    color = MaterialTheme.colorScheme.primary,
//                    modifier = Modifier.weight(1f)
//                )
//                Icon(
//                    imageVector = Icons.Default.Info,
//                    contentDescription = "Info",
//                    tint = MaterialTheme.colorScheme.primary
//                )
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            when (msg) {
//                "Battery Issues" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.batteryLevel.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                "Flat Tyre" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.flatTyre.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                "Overheating Engine" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.overheatingEngine.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                "Brake Problems" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.brakeProblem.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                "Headlight Failure" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.headlightFailure.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//
//                "Ignition Status" -> {
//                    Text(
//                        text = "$msg: ${vehicleData.ignitionStatus.value}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                }
//            }
//
//            AnimatedVisibility(visible = expanded) {
//                Column(modifier = Modifier.padding(top = 8.dp)) {
//                    item.steps.forEach { step ->
//                        Row(
//                            verticalAlignment = Alignment.CenterVertically,
//                            modifier = Modifier.padding(vertical = 4.dp)
//                        ) {
//                            Icon(
//                                imageVector = Icons.Default.Info,
//                                contentDescription = "Bullet",
//                                tint = MaterialTheme.colorScheme.secondary,
//                                modifier = Modifier.size(16.dp)
//                            )
//                            Spacer(modifier = Modifier.width(8.dp))
//                            Text(
//                                text = step,
//                                style = MaterialTheme.typography.bodyMedium
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
//}
//
//// ✅ Model class
//data class QuickFixItem(
//    val title: String,
//    val steps: List<String>
//)
//
//// ✅ Data
//val quickFixItems = listOf(
//    QuickFixItem(
//        "Battery Issues",
//        listOf(
//            "Check for corroded terminals",
//            "Ensure the battery is charged",
//            "Inspect battery cables for damage",
//            "Try jump-starting if dead"
//        )
//    ),
//    QuickFixItem(
//        "Flat Tyre",
//        listOf(
//            "Check tyre pressure using a gauge",
//            "Look for nails or punctures",
//            "Use spare tyre or tyre inflator",
//            "Ensure jack and wrench are available"
//        )
//    ),
//    QuickFixItem(
//        "Overheating Engine",
//        listOf(
//            "Turn off AC and pull over safely",
//            "Let the engine cool for 15–30 minutes",
//            "Check coolant level",
//            "Inspect for radiator leaks"
//        )
//    ),
//    QuickFixItem(
//        "Brake Problems",
//        listOf(
//            "Listen for squealing or grinding noises",
//            "Check brake fluid level",
//            "Avoid driving if pedal feels soft",
//            "Get brakes inspected immediately"
//        )
//    ),
//    QuickFixItem(
//        "Headlight Failure",
//        listOf(
//            "Check for blown fuses in fuse box",
//            "Replace burnt-out bulbs",
//            "Use hazard lights if visibility is low",
//            "Get replacement bulbs from nearby shop"
//        )
//    ),
//    QuickFixItem(
//        "Ignition Status",
//        listOf(
//            "Check if the battery is charged and properly connected",
//            "Ensure the key fob is detected (for push-to-start vehicles)",
//            "Inspect ignition fuse and replace if blown",
//            "Try turning the key/push-button again after waiting a few seconds",
//            "Listen for clicking or cranking sounds to diagnose starter issues",
//            "If the engine doesn't crank, have the starter motor inspected by a mechanic"
//        )
//    )
//)
//fun createNotificationChannel(context: Context) {
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "Ignition Status Warnings"
//        val descriptionText = "Notifies when ignition is in ACC, OFF or LOCK state"
//        val importance = NotificationManager.IMPORTANCE_HIGH
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//
//        val notificationManager: NotificationManager =
//            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}
//
//@RequiresPermission(Manifest.permission.POST_NOTIFICATIONS)
//fun showIgnitionWarning(context: Context, state: String) {
//    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
//        .setSmallIcon(R.drawable.stat_notify_error)
//        .setContentTitle("Ignition Warning")
//        .setContentText("Ignition is in $state state. Please check immediately.")
//        .setPriority(NotificationCompat.PRIORITY_HIGH)
//
//    val manager = NotificationManagerCompat.from(context)
//    manager.notify(NOTIFICATION_ID, builder.build())
//
//}

