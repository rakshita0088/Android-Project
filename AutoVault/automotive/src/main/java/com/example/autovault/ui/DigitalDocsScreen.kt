package com.example.autovault.ui



//import android.content.Context
//import android.content.Intent
//import android.net.Uri
//import android.provider.OpenableColumns
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.unit.dp
//import java.io.File
//import java.io.FileOutputStream
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun DigitalDocsScreen() {
//    val context = LocalContext.current
//    var savedFiles by remember { mutableStateOf(loadSavedFiles(context)) }
//
//    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.GetContent()
//    ) { uri: Uri? ->
//        uri?.let {
//            val fileName = getFileNameFromUri(context, it)
//            if (fileName != null) {
//                saveFileToInternalStorage(context, it, fileName)
//                savedFiles = loadSavedFiles(context) // refresh UI
//            }
//        }
//    }
//
//    Scaffold(
//        topBar = {
//            TopAppBar(title = { Text("Digital Docs") })
//        },
//        floatingActionButton = {
//            FloatingActionButton(onClick = { launcher.launch("*/*") }) {
//                Text("+")
//            }
//        }
//    ) { padding ->
//        LazyColumn(
//            modifier = Modifier
//                .padding(padding)
//                .padding(16.dp)
//        ) {
//            items(savedFiles) { file ->
//                Text(
//                    text = file.name,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .clickable { openFile(context, file) }
//                        .padding(12.dp)
//                )
//            }
//        }
//    }
//}
//
//// 📂 Save the selected file into internal storage
//fun saveFileToInternalStorage(context: Context, uri: Uri, fileName: String) {
//    val inputStream = context.contentResolver.openInputStream(uri)
//    val outputFile = File(context.filesDir, fileName)
//    val outputStream = FileOutputStream(outputFile)
//    inputStream?.copyTo(outputStream)
//    inputStream?.close()
//    outputStream.close()
//}
//
//// 📂 Load saved files from internal storage
//fun loadSavedFiles(context: Context): List<File> {
//    return context.filesDir.listFiles()?.toList() ?: emptyList()
//}
//
//// 📄 Get filename from URI
//fun getFileNameFromUri(context: Context, uri: Uri): String? {
//    val cursor = context.contentResolver.query(uri, null, null, null, null)
//    cursor?.use {
//        val nameIndex = it.getColumnIndexOpenableColumnsDisplayName()
//        if (it.moveToFirst() && nameIndex != -1) {
//            return it.getString(nameIndex)
//        }
//    }
//    return uri.lastPathSegment // fallback
//}
//
//// 🔎 Extension to get column index for file display name
//fun android.database.Cursor.getColumnIndexOpenableColumnsDisplayName(): Int {
//    return getColumnIndex(OpenableColumns.DISPLAY_NAME)
//}
//
//// 📤 Open saved file using external app
//fun openFile(context: Context, file: File) {
//    val uri = androidx.core.content.FileProvider.getUriForFile(
//        context,
//        "${context.packageName}.provider", // defined in AndroidManifest
//        file
//    )
//
//    val intent = Intent(Intent.ACTION_VIEW).apply {
//        setDataAndType(uri, context.contentResolver.getType(uri))
//        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//    }
//
//    context.startActivity(intent)
//}





import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey



@Composable
fun DigitalDocsScreen() {
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Digital Docs", style = MaterialTheme.typography.headlineMedium)

        Button(onClick = {
            openDriveDoc(context, "https://drive.google.com/file/d/1f2N0OsLBltmX_tomAWCUZ4CWLXgOvlxt/view?usp=sharing")
        }) {
            Text("Vehical Insurance")
        }

        Button(onClick = {
            openDriveDoc(context, "https://drive.google.com/file/d/1wROnNWcdeq74bTRmgaFJAvu_GDIvalhF/view?usp=sharing")
        }) {
            Text("Driving License")
        }

        Button(onClick = {
            openDriveDoc(context, "https://drive.google.com/file/d/1pjdfNAEL-93TLyMTS9BB2-q-JeMGoyOV/view?usp=sharing")
        }) {
            Text("Pollution_Certificate")
        }

        Button(onClick = {
            openDriveDoc(context, "https://drive.google.com/file/d/1AEWuBcJPMA8akFS2ebr0QvwaYGgYhhjz/view?usp=sharing")
        }) {
            Text("RC")
        }
    }
}

fun openDriveDoc(context: android.content.Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    context.startActivity(intent)
}


//@Composable
//fun DigitalDocsScreen() {
//    val context = LocalContext.current
//    var selectedFileName by remember { mutableStateOf<String?>(null) }
//    var storedFileUri by remember { mutableStateOf<String?>(loadFileUri(context)) }
//
//    // Launcher to pick file
//    val filePickerLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.OpenDocument(),
//        onResult = { uri: Uri? ->
//            uri?.let {
//                context.contentResolver.takePersistableUriPermission(
//                    it,
//                    Intent.FLAG_GRANT_READ_URI_PERMISSION
//                )
//                storeFileUri(context, it.toString())
//                storedFileUri = it.toString()
//                selectedFileName = getFileNameFromUri(context, it)
//            }
//        }
//    )
//
//    Column(modifier = Modifier.padding(16.dp)) {
//        Text("Upload or View Car Documents", style = MaterialTheme.typography.titleLarge)
//        Spacer(modifier = Modifier.height(16.dp))
//
//        Button(onClick = {
//            // Allow any file type like PDF, JPEG, etc.
//            filePickerLauncher.launch(arrayOf("*/*"))
//        }) {
//            Text("Upload RC Document")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Button(onClick = {
//            storedFileUri?.let { uriString ->
//                val intent = Intent(Intent.ACTION_VIEW).apply {
//                    data = Uri.parse(uriString)
//                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
//                }
//                context.startActivity(intent)
//            }
//        }, enabled = storedFileUri != null) {
//            Text("View Insurance Document")
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        selectedFileName?.let {
//            Text("Selected: $it", style = MaterialTheme.typography.bodyMedium)
//        }
//
//        if (storedFileUri == null) {
//            Text("No document uploaded yet", style = MaterialTheme.typography.bodySmall)
//        }
//    }
//}
//private fun storeFileUri(context: Context, uri: String) {
//    val masterKey = MasterKey.Builder(context)
//        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//        .build()
//
//    val sharedPrefs = EncryptedSharedPreferences.create(
//        context,
//        "secure_docs_prefs",
//        masterKey,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
//
//    sharedPrefs.edit().putString("document_uri", uri).apply()
//}
//
//private fun loadFileUri(context: Context): String? {
//    val masterKey = MasterKey.Builder(context)
//        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
//        .build()
//
//    val sharedPrefs = EncryptedSharedPreferences.create(
//        context,
//        "secure_docs_prefs",
//        masterKey,
//        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
//        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
//    )
//
//    return sharedPrefs.getString("document_uri", null)
//}
//
//private fun getFileNameFromUri(context: Context, uri: Uri): String? {
//    val cursor = context.contentResolver.query(uri, null, null, null, null)
//    return cursor?.use {
//        val nameIndex = it.getColumnIndex(OpenableColumns.DISPLAY_NAME)
//        it.moveToFirst()
//        it.getString(nameIndex)
//    }
//}



//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.unit.dp
//import androidx.navigation.NavController
//import com.example.autovault.ViewModel.DigitalDocsViewModel
//
//
//@Composable
//fun DigitalDocsListScreen(navController: NavController, viewModel: DigitalDocsViewModel) {
//    var title by remember { mutableStateOf("") }
//    var url by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.spacedBy(12.dp)
//    ) {
//        Text("Digital Documents", style = MaterialTheme.typography.headlineMedium)
//
//        OutlinedTextField(
//            value = title,
//            onValueChange = { title = it },
//            label = { Text("Document Title") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        OutlinedTextField(
//            value = url,
//            onValueChange = { url = it },
//            label = { Text("Google Drive URL") },
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        Button(
//            onClick = {
//                if (title.isNotBlank() && url.isNotBlank()) {
//                    viewModel.addDocument(com.example.autovault.data.DigitalDoc(title, url))
//                    title = ""
//                    url = ""
//                }
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("Add Document")
//        }
//
//        Button(
//            onClick = {
//                navController.navigate("details")
//            },
//            modifier = Modifier.fillMaxWidth()
//        ) {
//            Text("View Documents")
//        }
//    }
//}


