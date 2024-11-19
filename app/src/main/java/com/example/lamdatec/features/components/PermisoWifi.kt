package com.example.lamdatec.features.components

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.net.wifi.WifiManager
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermisosComposable(
    onPermissionsGranted: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    // Estados de permisos
    val permissionStateWifi =
        rememberPermissionState(permission = Manifest.permission.ACCESS_WIFI_STATE)
    val permissionStateChangeWifi =
        rememberPermissionState(permission = Manifest.permission.CHANGE_WIFI_STATE)
    val permissionStateNearbyWifi =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(permission = Manifest.permission.NEARBY_WIFI_DEVICES)
        } else null

    // Estado observable para permisos concedidos
    var permissionsGranted by remember {
        mutableStateOf(false)
    }

    // Actualiza el estado de permisos concedidos
    LaunchedEffect(permissionStateWifi.status, permissionStateChangeWifi.status, permissionStateNearbyWifi?.status) {
        permissionsGranted = permissionStateWifi.status.isGranted &&
                permissionStateChangeWifi.status.isGranted &&
                (permissionStateNearbyWifi?.status?.isGranted ?: true)
    }

    // Lógica para denegaciones permanentes
    val permisosDenegadosPermanentemente = !permissionStateWifi.status.shouldShowRationale &&
            !permissionStateChangeWifi.status.shouldShowRationale &&
            (permissionStateNearbyWifi?.status?.shouldShowRationale == false)

    // UI basada en estado
    when {
        permissionsGranted == true -> {
            // Muestra el contenido principal si los permisos fueron concedidos
            content()
        }
        permisosDenegadosPermanentemente == false -> {
            // Muestra la UI para permisos denegados permanentemente
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Permisos denegados. Dirígete a la configuración de la app y concede permisos.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text(text = "Ir a configuración")
                }
            }
        }
        else -> {
            // Muestra la UI para solicitar permisos
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(text = "Se requieren permisos para acceder al Wi-Fi.")
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
                        permissionStateWifi.launchPermissionRequest()
                        permissionStateChangeWifi.launchPermissionRequest()
                    } else {
                        permissionStateNearbyWifi?.launchPermissionRequest()
                    }
                }) {
                    Text(text = "Solicitar permisos")
                }
            }
        }
    }

    // Notifica si los permisos fueron concedidos
    LaunchedEffect(permissionsGranted) {
        onPermissionsGranted(permissionsGranted)
    }
}


// Lógica para activar Wi-Fi (con redirección en Android Q+)
fun WifiActivado(context: Context) {
    val wifiManager =
        context.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    if (!wifiManager.isWifiEnabled) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            wifiManager.isWifiEnabled = true
        } else {
            val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
            context.startActivity(intent)
        }
    }
}

// Composable para monitorear conexión
@Composable
fun InternetStatusComposable() {
    val context = LocalContext.current
    var isConnected by remember { mutableStateOf(false) }

    // Actualiza el estado de la conexión
    LaunchedEffect(Unit) {
        isConnected = HayInternet(context)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isConnected) "Estás conectado a Internet" else "No tienes conexión a Internet"
        )
        Button(onClick = {
            // Verifica la conexión manualmente
            isConnected = HayInternet(context)
        }) {
            Text(text = "Revisar conexión")
        }
    }
}

// Verificar conexión a Internet
fun HayInternet(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

