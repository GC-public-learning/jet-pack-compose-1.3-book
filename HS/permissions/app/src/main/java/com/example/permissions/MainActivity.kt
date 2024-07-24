package com.example.permissions

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.example.permissions.ui.theme.PermissionsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PermissionsTheme {
                // 1 permission
                var isCameraPermissionGranted by remember {
                    mutableStateOf(checkPermissionFor(Manifest.permission.CAMERA))
                }

                // to prompt permission 2nd time
                var showRationaleForCamera by remember { mutableStateOf(false) }

                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (isGranted) {
                            Log.d("myLogs", "camera permission true")
                            isCameraPermissionGranted = true
                        } else {
                            showRationaleForCamera =
                                shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)
                        }

                    }
                )

                // multiple permissions
                var isRecordAudioPermissionGranted by remember {
                    mutableStateOf(checkPermissionFor(Manifest.permission.RECORD_AUDIO))
                }
                var isCallPhonePermissionGranted by remember {
                    mutableStateOf(checkPermissionFor(Manifest.permission.CALL_PHONE))
                }
                var showRationaleForMultiplePermissions by remember { mutableStateOf(false) }

                val launcher2 = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { map ->
                        map.forEach { (permission, value) ->
                            Log.d("myLogs", "$permission = $value")
                            if (permission == Manifest.permission.RECORD_AUDIO) {
                                isRecordAudioPermissionGranted = value
                            }
                            if (permission == Manifest.permission.CALL_PHONE) {
                                isCallPhonePermissionGranted = value
                            }
                        }
                        // if one of or all permission not granted
                        if(map.values.any {!it}) {
                            showRationaleForMultiplePermissions = shouldShowRequestPermissionRationale(
                                Manifest.permission.RECORD_AUDIO) || shouldShowRequestPermissionRationale(
                                Manifest.permission.CALL_PHONE)
                        }
                    }
                )
                // UI
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 1 permission
                    if (showRationaleForCamera) {
                        PermissionRationaleDialog(
                            message = "Camera permission is required to use this feature. Please grant it in the app settings.",
                            onDismiss = { showRationaleForCamera = false },
                            onConfirm = { openAppSettings(); showRationaleForCamera = false }
                        )
                    }
                    // multipple permissions
                    if (showRationaleForMultiplePermissions) {
                        PermissionRationaleDialog(
                            message = "all permissions are required to use this feature. Please grant them in the app settings.",
                            onDismiss = { showRationaleForMultiplePermissions = false },
                            onConfirm = { openAppSettings(); showRationaleForMultiplePermissions = false }
                        )
                    }

                    // 1 permission
                    Button(onClick = {
                        if (!isCameraPermissionGranted) {
                            launcher.launch(Manifest.permission.CAMERA)
                        }
                    }) {
                        Text(text = "request camera permission $isCameraPermissionGranted")
                    }
                    // multiple permissions
                    Button(onClick = {
                        val notGrantedPermissionList = mutableListOf<String>()
                        if (!isRecordAudioPermissionGranted) {
                            notGrantedPermissionList.add(Manifest.permission.RECORD_AUDIO)
                        }
                        if (!isCallPhonePermissionGranted) {
                            notGrantedPermissionList.add(Manifest.permission.CALL_PHONE)
                        }
                        if (notGrantedPermissionList.isNotEmpty()) {
                            launcher2.launch(notGrantedPermissionList.toTypedArray())
                        }
                    }) {
                        Text(text = "request multiple permissions $isRecordAudioPermissionGranted $isCallPhonePermissionGranted")
                    }
                }
            }
        }
    }

    private fun checkPermissionFor(permission: String): Boolean =
        ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

    private fun openAppSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }

}

@Composable
fun PermissionRationaleDialog(
    message: String,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Permission Required") },
        text = { Text(text = message) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = "Open Settings")

            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = "Cancel")
            }
        }
    )
}

