package com.example.lamdatec.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lamdatec.Interfaz.Pantallas.GraficoMQ2_VISTA
import com.example.lamdatec.features.Graficos.MQ2.MQ2ViewModel
import com.example.lamdatec.features.Graficos.MQ2.viewM_MQ7
import com.example.lamdatec.features.Graficos.MQ7.GraficoMQ7_VISTA
import com.example.lamdatec.features.pPrincipal.presentation.Pantalla.PantallaInicio
import com.example.lamdatec.features.authentication.presentation.login.LoginScreen

@Composable
fun App(
) {
    val navController = rememberNavController()
    val viewModelMQ7: viewM_MQ7 = viewModel()
    val viewModelMQ2: MQ2ViewModel = viewModel()

    NavHost(
        navController = navController,   // El NavController que estás usando
        startDestination = PantallasNav.LOGIN.route   // La pantalla que será tu punto de inicio
    ) {
        composable(PantallasNav.PRINCIPAL.route) { PantallaInicio(navController) }
        composable(PantallasNav.SENSOR_MQ2.route) {
            GraficoMQ2_VISTA(navController, viewModelMQ2)
        }
        composable(PantallasNav.SENSOR_MQ7.route) {
            GraficoMQ7_VISTA(navController, viewModelMQ7)
        }
        composable(PantallasNav.LOGIN.route) {
            LoginScreen() { email, password ->
                // Navergar a la pantalla principal(FALTAN VALIDACIONES)
                navController.navigate(PantallasNav.PRINCIPAL.route) {

                    popUpTo(PantallasNav.LOGIN.route) {
                        inclusive = true
                    } // Evita volver a la pantalla de login

                }
            }
        }
    }
}

