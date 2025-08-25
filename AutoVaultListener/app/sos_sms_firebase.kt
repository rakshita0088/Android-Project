// ✅ Step 1: AutoVault App (on Emulator) - Sends SOS Alert with Live Location to Firebase

// File: MainActivity.kt
package com.example.autovault

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        firestore = FirebaseFirestore.getInstance()

        val sosButton: Button = findViewById(R.id.btnSOS)
        sosButton.setOnClickListener {
            sendEmergencyAlert()
        }
    }

    private fun sendEmergencyAlert() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
            return
        }

        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val latitude = it.latitude
                val longitude = it.longitude
                val mapsLink = "https://maps.google.com/?q=$latitude,$longitude"
                val message = "🚨 EMERGENCY! Please help me. Here's my location: $mapsLink"

                val alertData = hashMapOf(
                    "message" to message,
                    "phoneNumber" to "+91XXXXXXXXXX" // receiver number here
                )

                firestore.collection("alerts")
                    .add(alertData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "SOS Alert Sent!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Failed to send alert", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) sendEmergencyAlert()
            else Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show()
        }
}


// ✅ Step 2: Real Phone Firebase Listener (SMS Dispatcher)

// File: MainActivity.kt (for Real Phone)
package com.example.sosdispatcher

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class MainActivity : AppCompatActivity() {

    private lateinit var firestore: FirebaseFirestore
    private var listenerRegistration: ListenerRegistration? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Ask SMS permission
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.SEND_SMS),
            101
        )

        firestore = FirebaseFirestore.getInstance()

        listenToAlerts()
    }

    private fun listenToAlerts() {
        listenerRegistration = firestore.collection("alerts")
            .addSnapshotListener { snapshots, error ->
                if (error != null) {
                    Toast.makeText(this, "Error reading alerts", Toast.LENGTH_SHORT).show()
                    return@addSnapshotListener
                }

                for (doc in snapshots!!.documentChanges) {
                    val phone = doc.document.getString("phoneNumber")
                    val message = doc.document.getString("message")
                    if (!phone.isNullOrEmpty() && !message.isNullOrEmpty()) {
                        sendSms(phone, message)
                    }
                }
            }
    }

    private fun sendSms(phone: String, message: String) {
        try {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phone, null, message, null, null)
            Toast.makeText(this, "SMS sent to $phone", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "SMS sending failed: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        listenerRegistration?.remove()
    }
}
