package com.example.autovault

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.autovault.ViewModel.QuickFixHelpViewModel
import com.example.autovault.ViewModel.ServiceReminderViewModel
import com.example.autovault.data.car_api.GetVehicleData
import com.example.autovault.data.car_api.dto.VehicleData
import com.example.autovault.ui.DashboardScreen
import com.example.autovault.ui.DigitalDocsScreen
import com.example.autovault.ui.EmergencyPanelScreen
import com.example.autovault.ui.QuickFixHelpScreen
import com.example.autovault.ui.SecureVaultScreen
import com.example.autovault.ui.ServiceRemindersScreen
import com.example.autovault.ui.SetupScreen



//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AutoVaultApp(vehicleData: VehicleData) {
//    val navController = rememberNavController()
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("AutoVault Dashboard") })
//        }
//    ) { paddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = "dashboard",
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            composable("dashboard") { DashboardScreen(navController) }
//            composable("secureVault") {
//                SecureVaultScreen(navController)
//            }
//            composable("digitalDocs") { DigitalDocsScreen() }
//            composable("quickFix") { QuickFixHelpScreen(navController, vehicleData) }
//            composable("serviceReminder") {
//                val context = androidx.compose.ui.platform.LocalContext.current
//                ServiceRemindersScreen(context = context, viewModel = viewModel())
//            }
//            composable("setup") { SetupScreen() }
//        }
//    }
//}


//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.NavHostController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import com.example.autovault.ViewModel.ServiceReminderViewModel
//import com.example.autovault.data.car_api.dto.VehicleData
//import com.example.autovault.ui.*
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun AutoVaultApp(
//    vehicleData: VehicleData,
//    viewModel: ServiceReminderViewModel,
//    navController: NavHostController = rememberNavController()
//) {
//    val context = LocalContext.current
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("AutoVault Dashboard") })
//        }
//    ) { paddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = "dashboard",
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            composable("dashboard") { DashboardScreen(navController) }
//            composable("secureVault") { SecureVaultScreen(navController) }
//            composable("digitalDocs") { DigitalDocsScreen() }
//            composable("quickFix") { QuickFixHelpScreen(navController, vehicleData) }
//            composable("serviceReminder") {
//                ServiceRemindersScreen(context = context, viewModel = viewModel)
//            }
//            composable("log") {
//                ServiceLogListScreen(viewModel = viewModel)
//            }
//            composable("setup") { SetupScreen() }
//        }
//    }
//}


@RequiresApi(Build.VERSION_CODES.P)
@SuppressLint("ViewModelConstructorInComposable")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AutoVaultApp(getVehicleData: GetVehicleData) {
    val navController = rememberNavController()

    val viewModel: QuickFixHelpViewModel = remember {
        QuickFixHelpViewModel(getVehicleData)
    }
    val vehicleData = viewModel.state.value.status

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("AutoVault Dashboard") })
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = "dashboard",
            modifier = Modifier.padding(paddingValues)
        ) {
            composable("dashboard") {
                DashboardScreen(navController)
            }
            composable("secureVault") {
                SecureVaultScreen(navController)
            }
            composable("digitalDocs") {
                DigitalDocsScreen()
            }
            composable("quickFix") {
                QuickFixHelpScreen(navController, vehicleData?: VehicleData())
            }

            composable("serviceReminder") {
                val context = androidx.compose.ui.platform.LocalContext.current
                val viewModel = remember { ServiceReminderViewModel() }

                ServiceRemindersScreen(
                    context = context,
                    viewModel = viewModel
                )
            }

            composable("emergencySOS") {
                val context = LocalContext.current
                EmergencyPanelScreen(context)
            }



            composable("setup") {
                SetupScreen()
            }
        }
    }
}

