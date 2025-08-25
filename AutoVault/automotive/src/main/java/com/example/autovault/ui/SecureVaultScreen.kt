package com.example.autovault.ui
//
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//
//class SecureVaultScreen {
//
//    @Composable
//    fun SecureVaultScreen() {
//        Text("Secure Vault Screen")
//    }
//}


//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKeys
//
//@Composable
//fun SecureVaultScreen(navController: NavController) {
//    val context = LocalContext.current
//    val pinVerified = remember { mutableStateOf(false) }
//    val inputPin = remember { mutableStateOf("") }
//    val pinKey = "vault_pin"
//    val sharedPrefs by lazy {
//        EncryptedSharedPreferences.create(
//            "secure_vault_prefs",
//            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    // Check if PIN is already saved
//    val savedPin = sharedPrefs.getString(pinKey, null)
//
//    if (!pinVerified.value) {
//        Column(modifier = Modifier
//            .fillMaxSize()
//            .padding(24.dp),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = if (savedPin == null) "Set Vault PIN" else "Enter Vault PIN",
//                style = MaterialTheme.typography.titleLarge
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = inputPin.value,
//                onValueChange = { inputPin.value = it },
//                label = { Text("PIN") },
//                visualTransformation = PasswordVisualTransformation(),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(onClick = {
//                if (savedPin == null) {
//                    sharedPrefs.edit().putString(pinKey, inputPin.value).apply()
//                    pinVerified.value = true
//                    Toast.makeText(context, "PIN set successfully", Toast.LENGTH_SHORT).show()
//                } else if (inputPin.value == savedPin) {
//                    pinVerified.value = true
//                } else {
//                    Toast.makeText(context, "Wrong PIN", Toast.LENGTH_SHORT).show()
//                }
//            }, modifier = Modifier.fillMaxWidth()) {
//                Text(if (savedPin == null) "Set PIN" else "Unlock")
//            }
//        }
//        return
//    }
//
//    // Vault Field Keys and Labels
//    val vaultFields = listOf(
//        "Driving License No" to "dl_no",
//        "PAN / Aadhaar No" to "pan_aadhar",
//        "Insurance Policy No" to "insurance_no",
//        "Toll Card Info" to "toll_card",
//        "Bluetooth PIN" to "bt_pin",
//        "Service Center Contact" to "service_contact"
//    )
//
//    // Remember field values
//    val vaultData = remember { mutableStateMapOf<String, String>() }
//
//    // Load existing data
//    LaunchedEffect(Unit) {
//        vaultFields.forEach { (_, key) ->
//            vaultData[key] = sharedPrefs.getString(key, "") ?: ""
//        }
//    }
//
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(scrollState)
//    ) {
//        Text("Secure Vehicle Info Vault", style = MaterialTheme.typography.headlineSmall)
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        vaultFields.forEach { (label, key) ->
//            OutlinedTextField(
//                value = vaultData[key] ?: "",
//                onValueChange = { vaultData[key] = it },
//                label = { Text(label) },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(vertical = 6.dp)
//            )
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Save Button
//        Button(onClick = {
//            vaultFields.forEach { (_, key) ->
//                sharedPrefs.edit().putString(key, vaultData[key]).apply()
//            }
//            Toast.makeText(context, "All data saved securely!", Toast.LENGTH_SHORT).show()
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text("Save All")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Delete Button
//        Button(onClick = {
//            vaultFields.forEach { (_, key) ->
//                sharedPrefs.edit().remove(key).apply()
//                vaultData[key] = ""
//            }
//            Toast.makeText(context, "All data deleted!", Toast.LENGTH_SHORT).show()
//        }, modifier = Modifier.fillMaxWidth(), colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.error)) {
//            Text("Delete All")
//        }
//
//        Spacer(modifier = Modifier.height(12.dp))
//
//        // Back Button
//        OutlinedButton(onClick = {
//            navController.popBackStack()
//        }, modifier = Modifier.fillMaxWidth()) {
//            Text("← Back to Dashboard")
//        }
//    }
//}


//import android.content.Context
//import android.os.Build
//import androidx.annotation.RequiresApi
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.PasswordVisualTransformation
//import androidx.compose.ui.unit.dp
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKey
//import androidx.navigation.NavController
//
//@RequiresApi(Build.VERSION_CODES.M)
//@Composable
//fun SecureVaultScreen(navController: NavController) {
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//
//    val fields = listOf(
//        "Driving License Number", "Toll Card Info", "PAN/Aadhaar Linked Info",
//        "Service Center Contact", "Bluetooth Pairing PIN", "Insurance Policy Number"
//    )
//
//    val fieldStates = remember { mutableStateMapOf<String, String>() }
//    val pinState = remember { mutableStateOf("") }
//    val enteredPin = remember { mutableStateOf("") }
//    val isAuthenticated = remember { mutableStateOf(false) }
//    val showError = remember { mutableStateOf(false) }
//
//    val prefs = remember {
//        val masterKey = MasterKey.Builder(context)
//            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//            .build()
//
//        EncryptedSharedPreferences.create(
//            context,
//            "secure_vault_prefs",
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    // Load saved fields
//    LaunchedEffect(Unit) {
//        fields.forEach { key ->
//            fieldStates[key] = prefs.getString(key, "") ?: ""
//        }
//        pinState.value = prefs.getString("vault_pin", "") ?: ""
//    }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(scrollState)
//    ) {
//        Text("Secure Vault", style = MaterialTheme.typography.headlineMedium)
//
//        Spacer(Modifier.height(16.dp))
//
//        if (!isAuthenticated.value) {
//            OutlinedTextField(
//                value = enteredPin.value,
//                onValueChange = {
//                    enteredPin.value = it
//                    showError.value = false
//                },
//                label = { Text("Enter PIN to unlock") },
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            if (showError.value) {
//                Text("Incorrect PIN!", color = MaterialTheme.colorScheme.error)
//            }
//
//            Spacer(Modifier.height(8.dp))
//
//            Button(onClick = {
//                if (enteredPin.value == pinState.value) {
//                    isAuthenticated.value = true
//                } else {
//                    showError.value = true
//                }
//            }) {
//                Text("Unlock")
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            Text("If this is your first time, please set a PIN below:")
//            OutlinedTextField(
//                value = pinState.value,
//                onValueChange = { pinState.value = it },
//                label = { Text("Set PIN") },
//                visualTransformation = PasswordVisualTransformation(),
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(Modifier.height(8.dp))
//
//            Button(onClick = {
//                prefs.edit().putString("vault_pin", pinState.value).apply()
//                isAuthenticated.value = true
//            }) {
//                Text("Save PIN and Continue")
//            }
//
//        } else {
//            fields.forEach { label ->
//                OutlinedTextField(
//                    value = fieldStates[label] ?: "",
//                    onValueChange = { fieldStates[label] = it },
//                    label = { Text(label) },
//                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp)
//                )
//
//                Row(
//                    horizontalArrangement = Arrangement.spacedBy(8.dp),
//                    modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
//                ) {
//                    Button(onClick = {
//                        prefs.edit().putString(label, fieldStates[label]).apply()
//                    }) {
//                        Text("Save")
//                    }
//
//                    OutlinedButton(onClick = {
//                        fieldStates[label] = ""
//                        prefs.edit().remove(label).apply()
//                    }) {
//                        Text("Delete")
//                    }
//                }
//            }
//
//            Spacer(Modifier.height(16.dp))
//
//            Button(onClick = { navController.popBackStack() }) {
//                Text("Back to Dashboard")
//            }
//        }
//    }
//}

//
//import android.content.Context
//import android.widget.Toast
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKeys
//
//@Composable
//fun SecureVaultScreen(onBack: () -> Unit) {
//    val context = LocalContext.current
//    val scrollState = rememberScrollState()
//
//    // Input states
//    var license by remember { mutableStateOf("") }
//    var tollCard by remember { mutableStateOf("") }
//    var panAadhar by remember { mutableStateOf("") }
//    var insurancePolicy by remember { mutableStateOf("") }
//    var serviceContact by remember { mutableStateOf("") }
//    var bluetoothPin by remember { mutableStateOf("") }
//    var userPin by remember { mutableStateOf("") }
//
//    // Pin input for viewing
//    var viewPin by remember { mutableStateOf("") }
//    var currentViewingKey by remember { mutableStateOf<String?>(null) }
//
//    val prefs = remember {
//        EncryptedSharedPreferences.create(
//            "secure_vault",
//            MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC),
//            context,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    fun saveAll() {
//        if (userPin.isEmpty()) {
//            Toast.makeText(context, "Please set a pin first", Toast.LENGTH_SHORT).show()
//            return
//        }
//        prefs.edit().apply {
//            putString("PIN", userPin)
//            putString("LICENSE", license)
//            putString("TOLLCARD", tollCard)
//            putString("PANAADHAR", panAadhar)
//            putString("INSURANCE", insurancePolicy)
//            putString("SERVICE", serviceContact)
//            putString("BLUETOOTH", bluetoothPin)
//            apply()
//        }
//        Toast.makeText(context, "All data saved securely", Toast.LENGTH_SHORT).show()
//        license = tollCard = panAadhar = insurancePolicy = serviceContact = bluetoothPin = ""
//    }
//
//    fun checkAndView(key: String, label: String) {
//        val savedPin = prefs.getString("PIN", "") ?: ""
//        if (viewPin == savedPin) {
//            currentViewingKey = key
//        } else {
//            Toast.makeText(context, "Incorrect PIN", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    fun deleteItem(key: String) {
//        prefs.edit().remove(key).apply()
//        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show()
//        currentViewingKey = null
//    }
//
//    val itemLabels = mapOf(
//        "LICENSE" to "Driving License",
//        "TOLLCARD" to "Toll Card Info",
//        "PANAADHAR" to "PAN / Aadhar",
//        "INSURANCE" to "Insurance Policy",
//        "SERVICE" to "Service Contact",
//        "BLUETOOTH" to "Bluetooth Pin"
//    )
//
//    BackHandler { onBack() }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(scrollState)
//            .padding(16.dp)
//    ) {
//        Text("Secure Vault", style = MaterialTheme.typography.titleLarge)
//        Spacer(Modifier.height(12.dp))
//
//        OutlinedTextField(value = license, onValueChange = { license = it }, label = { Text("Driving License Number") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = tollCard, onValueChange = { tollCard = it }, label = { Text("Toll Card Info") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = panAadhar, onValueChange = { panAadhar = it }, label = { Text("PAN / Aadhar") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = insurancePolicy, onValueChange = { insurancePolicy = it }, label = { Text("Insurance Policy No.") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = serviceContact, onValueChange = { serviceContact = it }, label = { Text("Service Center Contact") }, modifier = Modifier.fillMaxWidth())
//        OutlinedTextField(value = bluetoothPin, onValueChange = { bluetoothPin = it }, label = { Text("Bluetooth Pairing Pin") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(Modifier.height(10.dp))
//        OutlinedTextField(value = userPin, onValueChange = { userPin = it }, label = { Text("Set 4-digit Pin") }, modifier = Modifier.fillMaxWidth())
//
//        Spacer(Modifier.height(16.dp))
//        Button(onClick = { saveAll() }, modifier = Modifier.fillMaxWidth()) {
//            Text("Save All Securely")
//        }
//
//        Spacer(Modifier.height(24.dp))
//        Text("🔐 View/Delete Secure Info", style = MaterialTheme.typography.titleMedium)
//        OutlinedTextField(value = viewPin, onValueChange = { viewPin = it }, label = { Text("Enter PIN to View") }, modifier = Modifier.fillMaxWidth())
//
//        itemLabels.forEach { (key, label) ->
//            Spacer(Modifier.height(8.dp))
//            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
//                Button(onClick = { checkAndView(key, label) }, modifier = Modifier.weight(1f)) {
//                    Text("View $label")
//                }
//                Spacer(Modifier.width(8.dp))
//                Button(onClick = { deleteItem(key) }, modifier = Modifier.weight(1f)) {
//                    Text("Delete")
//                }
//            }
//
//            // Show data if selected and pin is correct
//            if (currentViewingKey == key) {
//                val value = prefs.getString(key, "Not Found") ?: "Not Found"
//                Text("$label: $value", modifier = Modifier.padding(top = 4.dp))
//            }
//        }
//
//        Spacer(Modifier.height(32.dp))
//        Button(onClick = { onBack() }, modifier = Modifier.fillMaxWidth()) {
//            Text("⬅ Back to Dashboard")
//        }
//    }
//}


//import android.content.Context
//import android.widget.Toast
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowBack
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.unit.dp
//import androidx.security.crypto.EncryptedSharedPreferences
//import androidx.security.crypto.MasterKey
//import androidx.navigation.NavController
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SecureVaultScreen(navController: NavController) {
//    val context = LocalContext.current
//
//    var pinInput by remember { mutableStateOf("") }
//    var enteredPin by remember { mutableStateOf("") }
//
//    var license by remember { mutableStateOf("") }
//    var tollCard by remember { mutableStateOf("") }
//    var panAadhar by remember { mutableStateOf("") }
//    var insurancePolicy by remember { mutableStateOf("") }
//    var serviceContact by remember { mutableStateOf("") }
//    var bluetoothPin by remember { mutableStateOf("") }
//
//    val scrollState = rememberScrollState()
//    val sharedPrefs = remember {
//        val masterKey = MasterKey.Builder(context)
//            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//            .build()
//
//        EncryptedSharedPreferences.create(
//            context,
//            "secure_vault_prefs",
//            masterKey,
//            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//        )
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(
//                title = { Text("Secure Vault") },
//                navigationIcon = {
//                    IconButton(onClick = { navController.navigateUp() }) {
//                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
//                    }
//                }
//            )
//        }
//    ) { innerPadding ->
//        Column(
//            modifier = Modifier
//                .padding(innerPadding)
//                .padding(16.dp)
//                .verticalScroll(scrollState)
//        ) {
//            OutlinedTextField(
//                value = enteredPin,
//                onValueChange = { enteredPin = it },
//                label = { Text("Enter PIN to access info") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            Button(onClick = {
//                pinInput = sharedPrefs.getString("vault_pin", "") ?: ""
//
//                if (enteredPin == pinInput) {
//                    license = sharedPrefs.getString("license", "") ?: ""
//                    tollCard = sharedPrefs.getString("tollCard", "") ?: ""
//                    panAadhar = sharedPrefs.getString("panAadhar", "") ?: ""
//                    insurancePolicy = sharedPrefs.getString("insurancePolicy", "") ?: ""
//                    serviceContact = sharedPrefs.getString("serviceContact", "") ?: ""
//                    bluetoothPin = sharedPrefs.getString("bluetoothPin", "") ?: ""
//
//                    Toast.makeText(context, "Access granted", Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(context, "Incorrect PIN", Toast.LENGTH_SHORT).show()
//                }
//            }) {
//                Text("Unlock")
//            }
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text("Enter Info (Only visible after unlocking)", style = MaterialTheme.typography.titleMedium)
//
//            InfoField("Driving License No", license, onDelete = {
//                sharedPrefs.edit().remove("license").apply()
//                license = ""
//            }, onView = { Toast.makeText(context, license, Toast.LENGTH_SHORT).show() })
//
//            InfoField("Toll Card Info", tollCard, onDelete = {
//                sharedPrefs.edit().remove("tollCard").apply()
//                tollCard = ""
//            }, onView = { Toast.makeText(context, tollCard, Toast.LENGTH_SHORT).show() })
//
//            InfoField("PAN/Aadhar Linked", panAadhar, onDelete = {
//                sharedPrefs.edit().remove("panAadhar").apply()
//                panAadhar = ""
//            }, onView = { Toast.makeText(context, panAadhar, Toast.LENGTH_SHORT).show() })
//
//            InfoField("Insurance Policy No", insurancePolicy, onDelete = {
//                sharedPrefs.edit().remove("insurancePolicy").apply()
//                insurancePolicy = ""
//            }, onView = { Toast.makeText(context, insurancePolicy, Toast.LENGTH_SHORT).show() })
//
//            InfoField("Service Center Contact", serviceContact, onDelete = {
//                sharedPrefs.edit().remove("serviceContact").apply()
//                serviceContact = ""
//            }, onView = { Toast.makeText(context, serviceContact, Toast.LENGTH_SHORT).show() })
//
//            InfoField("Bluetooth Pairing PIN", bluetoothPin, onDelete = {
//                sharedPrefs.edit().remove("bluetoothPin").apply()
//                bluetoothPin = ""
//            }, onView = { Toast.makeText(context, bluetoothPin, Toast.LENGTH_SHORT).show() })
//
//            Spacer(modifier = Modifier.height(24.dp))
//
//            Text("Enter New Values & Save", style = MaterialTheme.typography.titleMedium)
//
//            OutlinedTextField(
//                value = license,
//                onValueChange = { license = it },
//                label = { Text("Driving License No") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = tollCard,
//                onValueChange = { tollCard = it },
//                label = { Text("Toll Card Info") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = panAadhar,
//                onValueChange = { panAadhar = it },
//                label = { Text("PAN or Aadhar Linked") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = insurancePolicy,
//                onValueChange = { insurancePolicy = it },
//                label = { Text("Insurance Policy No") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = serviceContact,
//                onValueChange = { serviceContact = it },
//                label = { Text("Service Center Contact") },
//                modifier = Modifier.fillMaxWidth()
//            )
//            OutlinedTextField(
//                value = bluetoothPin,
//                onValueChange = { bluetoothPin = it },
//                label = { Text("Bluetooth Pairing PIN") },
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            OutlinedTextField(
//                value = pinInput,
//                onValueChange = { pinInput = it },
//                label = { Text("Set/Update PIN") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Button(
//                onClick = {
//                    sharedPrefs.edit().apply {
//                        putString("license", license)
//                        putString("tollCard", tollCard)
//                        putString("panAadhar", panAadhar)
//                        putString("insurancePolicy", insurancePolicy)
//                        putString("serviceContact", serviceContact)
//                        putString("bluetoothPin", bluetoothPin)
//                        putString("vault_pin", pinInput)
//                        apply()
//                    }
//                    Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show()
//                },
//                modifier = Modifier.align(Alignment.End)
//            ) {
//                Text("Save All")
//            }
//
//            Spacer(modifier = Modifier.height(50.dp))
//        }
//    }
//}
//
//@Composable
//fun InfoField(title: String, value: String, onDelete: () -> Unit, onView: () -> Unit) {
//    if (value.isNotBlank()) {
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp),
//            horizontalArrangement = Arrangement.SpaceBetween
//        ) {
//            Text(title)
//            Row {
//                Text("View", modifier = Modifier
//                    .clickable { onView() }
//                    .padding(end = 8.dp), color = MaterialTheme.colorScheme.primary)
//
//                Text("Delete", modifier = Modifier
//                    .clickable { onDelete() },
//                    color = MaterialTheme.colorScheme.error)
//            }
//        }
//    }
//}


import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@Composable
fun SecureVaultScreen(navController: NavController) {
    val context = LocalContext.current

    var pin by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var info by remember { mutableStateOf("") }
    var enteredPin by remember { mutableStateOf("") }
    var isUnlocked by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    var savedEntries by remember { mutableStateOf(loadSavedEntries(context)) }

    val correctPin = "0808"
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Text("Secure Vault", style = MaterialTheme.typography.headlineSmall)

        Spacer(Modifier.height(16.dp))

        // Enter PIN to Unlock
        if (!isUnlocked) {
            OutlinedTextField(
                value = enteredPin,
                onValueChange = { enteredPin = it },
                label = { Text("Enter PIN to Unlock") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(Modifier.height(8.dp))
            Button(onClick = {
                if (enteredPin == correctPin) {
                    isUnlocked = true
                    errorMessage = ""
                } else {
                    errorMessage = "PIN incorrect"
                }
            }, modifier = Modifier.fillMaxWidth()) {
                Text("Unlock")
            }

            if (errorMessage.isNotEmpty()) {
                Text(errorMessage, color = MaterialTheme.colorScheme.error)
            }
        } else {
            // Unlocked - Show fields to add new entry
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title (e.g., Toll, Bank PIN)") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = info,
                onValueChange = { info = it },
                label = { Text("Secret Info") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (title.isNotEmpty() && info.isNotEmpty()) {
                        val newEntry = VaultEntry(title, info)
                        savedEntries = savedEntries + newEntry
                        saveEntries(context, savedEntries)
                        title = ""
                        info = ""
                        Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Save")
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text("Saved Entries:", style = MaterialTheme.typography.titleMedium)
            Spacer(modifier = Modifier.height(8.dp))

            LazyColumn {
                items(savedEntries.size) { index ->
                    val entry = savedEntries[index]
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(12.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column {
                                Text(entry.title, style = MaterialTheme.typography.titleSmall)
                                Text(entry.info, style = MaterialTheme.typography.bodyMedium)
                            }

                            Text(
                                "❌",
                                modifier = Modifier.clickable {
                                    savedEntries = savedEntries.toMutableList().apply {
                                        removeAt(index)
                                    }
                                    saveEntries(context, savedEntries)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

data class VaultEntry(val title: String, val info: String)

fun saveEntries(context: Context, entries: List<VaultEntry>) {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val prefs = EncryptedSharedPreferences.create(
        "secure_vault_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val json = Gson().toJson(entries)
    prefs.edit().putString("entries", json).apply()
}

fun loadSavedEntries(context: Context): List<VaultEntry> {
    val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
    val prefs = EncryptedSharedPreferences.create(
        "secure_vault_prefs",
        masterKeyAlias,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )
    val json = prefs.getString("entries", null)
    return if (json != null) {
        val type = object : TypeToken<List<VaultEntry>>() {}.type
        Gson().fromJson(json, type)
    } else {
        emptyList()
    }
}
