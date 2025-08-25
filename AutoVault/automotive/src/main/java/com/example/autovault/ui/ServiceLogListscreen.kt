package com.example.autovault.ui
//
////class ServiceLogListscreen {
////}package com.example.autovault.ui
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.autovault.ViewModel.ServiceReminderViewModel
//import kotlinx.coroutines.flow.collectLatest
//
//@Composable
//fun ServiceLogListScreen(viewModel: ServiceReminderViewModel = viewModel()) {
//    val logs by viewModel.logs.collectAsState()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Service Log History", style = MaterialTheme.typography.headlineSmall)
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        LazyColumn {
//            items(logs) { log ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 4.dp),
//                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text("Last Service: ${log.lastServiceDate}")
//                        Text("Next Due: ${log.nextServiceDate}")
//                    }
//                }
//            }
//        }
//    }
//}


import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.autovault.ViewModel.ServiceReminderViewModel


//@Composable
//fun ServiceLogListScreen(viewModel: ServiceReminderViewModel) {
//    val logs = viewModel.getServiceLogs()
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Service History", style = MaterialTheme.typography.headlineSmall)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        logs.forEach {
//            Text("- $it")
//        }
//    }
//}

@Composable
fun ServiceLogListScreen(viewModel: ServiceReminderViewModel) {
    val logs by viewModel.reminders.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Service History", style = MaterialTheme.typography.headlineSmall)
        Spacer(modifier = Modifier.height(8.dp))

        logs.forEach {
            Text("- $it")
        }
    }
}
