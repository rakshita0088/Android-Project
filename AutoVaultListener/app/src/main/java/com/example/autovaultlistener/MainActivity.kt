package com.example.autovaultlistener

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.example.autovaultlistener.ui.screens.VehicleHealthScreen
import com.example.autovaultlistener.ui.viewmodel.VehicleViewModel
import com.example.autovaultlistener.worker.VehicleEmergencyCheckScheduler

//import com.google.firebase.FirebaseApp

class MainActivity : ComponentActivity() {

    // Permission launcher for SEND/READ/RECEIVE SMS
    private val smsPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions[Manifest.permission.SEND_SMS] == true &&
                permissions[Manifest.permission.READ_SMS] == true &&
                permissions[Manifest.permission.RECEIVE_SMS] == true

        if (!granted) {
            Toast.makeText(this, "SMS permissions are required to send alerts!", Toast.LENGTH_LONG).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase (important for Firestore/Firebase Cloud Messaging)
//        FirebaseApp.initializeApp(this)

        // Request SMS-related permissions
        smsPermissionLauncher.launch(
            arrayOf(
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.POST_NOTIFICATIONS
            )
        )


        VehicleEmergencyCheckScheduler(applicationContext)



        // Set Compose UI content
        setContent {
            val viewModel = VehicleViewModel()
            MaterialTheme {
//                SOSListenerScreen() // Your Composable function
                VehicleHealthScreen(viewModel)
            }
        }
    }
}
@Composable
fun SOSListenerScreen() {
    val context = LocalContext.current

    androidx.compose.material3.Text(
        text = "AutoVault Listener is Active!",
        style = MaterialTheme.typography.headlineMedium
    )
}


