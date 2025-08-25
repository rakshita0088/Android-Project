package com.example.autovault


//
//import android.Manifest
//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.material3.*
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.autovault.data.car_api.GetVehicleData
//import com.example.autovault.data.car_api.dto.VehicleData
//import com.example.autovault.ui.*
//
//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Ask for POST_NOTIFICATIONS permission if Android 13+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
//        }
//
//        val getVehicleData = GetVehicleData(this)
//
//        setContent {
//
//            val navController = rememberNavController()
//
//            NavHost(navController = navController, startDestination = "input") {
//                composable("input") {
//                    val viewModel = ViewModelProvider(
//                        this@MainActivity,
//                        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//                    )[ServiceReminderViewModel::class.java]
//
//                    ServiceRemindersScreen(context = this@MainActivity, viewModel = viewModel)
//                }
//                composable("log") {
//                    val viewModel = ViewModelProvider(
//                        this@MainActivity,
//                        ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//                    )[ServiceReminderViewModel::class.java]
//
//                    ServiceLogListScreen(viewModel = viewModel)
//                }
//            }
//        }
//
//            var vehicleData by remember {
//                mutableStateOf(
//                    VehicleData() // use a fallback or loading state
//                )
//            }
//
//            LaunchedEffect(Unit) {
//                val result = getVehicleData.subscribeVehicleProperties()
//                vehicleData = result
//            }
//            AutoVaultApp(vehicleData)
//        }
//    }





//import android.os.Build
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.navigation.NavType
//import androidx.navigation.compose.*
//import com.example.autovault.data.car_api.GetVehicleData
//import com.example.autovault.data.car_api.dto.VehicleData
//import com.example.autovault.ui.ServiceLogListScreen
//import com.example.autovault.ui.ServiceRemindersScreen
//import androidx.compose.ui.Modifier
//
//
//
//class MainActivity : ComponentActivity() {
//
//    @OptIn(ExperimentalMaterial3Api::class)
//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        // Ask for notification permission on Android 13+
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestPermissions(arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 1001)
//        }
//
//        val getVehicleData = GetVehicleData(this)
//
//        setContent {
//            var vehicleData by remember {
//                mutableStateOf(VehicleData())
//            }
//
//            // Fetch vehicle data once
//            LaunchedEffect(Unit) {
//                val result = getVehicleData.subscribeVehicleProperties()
//                vehicleData = result
//                Log.d("VehicleData", "Fetched: $vehicleData")
//            }
//
//            val navController = rememberNavController()
//
//            Scaffold(
//                topBar = {
//                    TopAppBar(
//                        title = { Text("AutoVault Service Reminder") }
//                    )
//                }
//            ) { padding ->
//                NavHost(
//                    navController = navController,
//                    startDestination = "input",
//                    modifier = Modifier.padding(padding)
//                ) {
//                    composable("input") {
//                        ServiceRemindersScreen(
//                            context = this@MainActivity,
//                            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//                        )
//                    }
//                    composable("log") {
//                        ServiceLogListScreen(
//                            viewModel = androidx.lifecycle.viewmodel.compose.viewModel()
//                        )
//                    }
//                }
//            }
//        }
//    }
//}



//
//import android.Manifest
//import android.os.Build
//import android.os.Bundle
//import androidx.activity.ComponentActivity
//import androidx.activity.compose.setContent
//import androidx.annotation.RequiresApi
//import androidx.compose.runtime.*
//import androidx.lifecycle.ViewModelProvider
//import androidx.navigation.compose.rememberNavController
//import com.example.autovault.ViewModel.ServiceReminderViewModel
//import com.example.autovault.data.car_api.GetVehicleData
//import com.example.autovault.data.car_api.dto.VehicleData
//import com.example.autovault.theme.AutoVaultTheme


//class MainActivity : ComponentActivity() {
//    @RequiresApi(Build.VERSION_CODES.P)
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//            requestPermissions(arrayOf(Manifest.permission.POST_NOTIFICATIONS), 1001)
//        }
//
//        val getVehicleData = GetVehicleData(this)
//
//        setContent {
//            AutoVaultTheme {
//                var vehicleData by remember { mutableStateOf(VehicleData()) }
//
//                LaunchedEffect(Unit) {
//                    val result = getVehicleData.subscribeVehicleProperties()
//                    vehicleData = result
//                }
//
//                val navController = rememberNavController()
//
//                val viewModel = ViewModelProvider(
//                    this@MainActivity,
//                    ViewModelProvider.AndroidViewModelFactory.getInstance(application)
//                )[ServiceReminderViewModel::class.java]
//
//                AutoVaultApp(
//                    vehicleData = vehicleData,
//                    viewModel = viewModel,
//                    navController = navController
//                )
//            }
//        }
//    }
//}



import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.material3.*
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autovault.ViewModel.QuickFixHelpViewModel
import com.example.autovault.data.car_api.GetVehicleData
import com.example.autovault.data.car_api.dto.VehicleData
import com.example.autovault.ui.*
import com.example.autovault.worker.ReminderServiceScheduler
import com.example.autovault.worker.VehicleEmergencyCheckScheduler
import com.example.autovault.worker.sendNotification

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val getVehicleData = GetVehicleData(this)
//        val viewModel = QuickFixHelpViewModel(getVehicleData)
//        val vehicleData = viewModel.state.value.status


        setContent {

            sendNotification("Vehicle Service Due Today!", applicationContext)

//            ReminderServiceScheduler(applicationContext)
//            VehicleEmergencyCheckScheduler(applicationContext)

//            var vehicleData by remember {
//                mutableStateOf(
//                    VehicleData() // use a fallback or loading state
//                )
//            }

            LaunchedEffect(Unit) {
//                val result = getVehicleData.subscribeVehicleProperties()
//                vehicleData = result
            }
            AutoVaultApp(getVehicleData)
        }
    }


}


