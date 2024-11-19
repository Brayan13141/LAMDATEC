package com.example.lamdatec.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.lamdatec.app.theme.LAMDATECTheme
import com.example.lamdatec.core.navigation.App
import com.example.lamdatec.features.components.HayInternet
import com.example.lamdatec.features.components.PermisosComposable
import com.example.lamdatec.features.components.WifiActivado
import com.example.lamdatec.features.pPrincipal.data.Worker.ActualizarHora
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        ActualizarHora(this)
        enableEdgeToEdge()
        setContent {
            LAMDATECTheme(
                darkTheme = false,
                dynamicColor = false
            ) {

                var Ban = false
                PermisosComposable({ granted ->
                    Ban = granted
                }) {
                    if (Ban) {
                        WifiActivado(this)  // Habilitar el Wi-Fi si es necesario
                        if(HayInternet(this)){
                        App()
                        }
                    }
                }

            }
        }
    }
}

