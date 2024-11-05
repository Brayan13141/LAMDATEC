package com.example.lamdatec.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import com.example.lamdatec.core.navigation.App
import com.example.lamdatec.app.theme.LAMDATECTheme
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
            LAMDATECTheme {
               App()
            }
        }
    }
}

