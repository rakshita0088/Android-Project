package com.example.autovault.location

import com.google.firebase.firestore.GeoPoint

//class LocationTracker {
//}
//package com.example.autovault.location

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.google.android.gms.location.*

class LocationTracker(private val context: Context) {

    private var fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    private var locationCallback: LocationCallback? = null

    @SuppressLint("MissingPermission")
    fun startTracking(onLocationReceived: (Location) -> Unit) {
        val locationRequest = LocationRequest.create().apply {
            interval = 10000  // 10 seconds
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    onLocationReceived(location)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback as LocationCallback,
            context.mainLooper
        )
    }

    fun stopTracking() {
        locationCallback?.let {
            fusedLocationClient.removeLocationUpdates(it)
        }
    }
}





//import android.Manifest
//import android.annotation.SuppressLint
//import android.content.Context
//import android.content.pm.PackageManager
//import android.location.Location
//import androidx.core.content.ContextCompat
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationServices
//import kotlinx.coroutines.suspendCancellableCoroutine
//import kotlin.coroutines.resume
//
//class LocationTracker(
//    private val context: Context
//) {
//    private val fusedLocationClient: FusedLocationProviderClient =
//        LocationServices.getFusedLocationProviderClient(context)
//
//    @SuppressLint("MissingPermission")
//    suspend fun getCurrentLocation(): GeoPoint? {
//        val hasAccessFineLocation = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_FINE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        val hasAccessCoarseLocation = ContextCompat.checkSelfPermission(
//            context,
//            Manifest.permission.ACCESS_COARSE_LOCATION
//        ) == PackageManager.PERMISSION_GRANTED
//
//        if (!hasAccessFineLocation && !hasAccessCoarseLocation) {
//            return null
//        }
//
//        return suspendCancellableCoroutine { continuation ->
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location: Location? ->
//                    if (location != null) {
//                        continuation.resume(GeoPoint(location.latitude, location.longitude))
//                    } else {
//                        continuation.resume(null)
//                    }
//                }
//                .addOnFailureListener {
//                    continuation.resume(null)
//                }
//        }
//    }
//}
