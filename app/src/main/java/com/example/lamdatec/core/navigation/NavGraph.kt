package com.example.lamdatec.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lamdatec.Interfaz.Pantallas.GraficoMQ2_VISTA
import com.example.lamdatec.features.Graficos.MQ7.GraficoMQ7_VISTA
import com.example.lamdatec.features.pPrincipal.presentation.Pantalla.PantallaInicio
import com.example.lamdatec.features.authentication.presentation.login.LoginScreen

@Composable
fun App(
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,   // El NavController que estás usando
        startDestination = Destinations.Login.route   // La pantalla que será tu punto de inicio
    ) {
        composable(Destinations.Principal.route) {
            PantallaInicio(
                navController = navController
            )
        }
        composable(Destinations.SensorMQ2.route) {
            GraficoMQ2_VISTA(navController)
        }
        composable(Destinations.SensorMQ7.route) {
            GraficoMQ7_VISTA(navController)
        }
        composable(Destinations.Login.route) {
            LoginScreen() { email, password ->
                // Navergar a la pantalla principal(FALTAN VALIDACIONES)
                navController.navigate(Destinations.Principal.route) {

                    popUpTo(0) {
                        inclusive = true
                    } // Evita volver a la pantalla de login

                }
            }
        }
    }
}

