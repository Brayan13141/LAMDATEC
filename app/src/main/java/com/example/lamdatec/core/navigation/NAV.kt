package com.example.lamdatec.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lamdatec.Interfaz.Pantallas.PantallaConGrafico
import com.example.lamdatec.Interfaz.Pantallas.PantallaInicio
import com.example.lamdatec.features.authentication.presentation.login.LoginScreen

@Composable
fun App(
) {
    val navController = rememberNavController()
            NavHost(
                navController = navController,   // El NavController que estás usando
                startDestination = PantallasNav.LOGIN.route   // La pantalla que será tu punto de inicio
            ) {
                composable( PantallasNav.PRINCIPAL.route) { PantallaInicio(navController) }
                composable( PantallasNav.S1.route) {  PantallaConGrafico(navController) }
                composable( PantallasNav.S2.route) {  PantallaConGrafico(navController) }
                composable( PantallasNav.LOGIN.route) {
                    LoginScreen() { email, password ->
                    // Aquí puedes agregar tu lógica de autenticación (ejemplo: Firebase)
                    // Si el inicio de sesión es exitoso, navega a la pantalla principal
                    navController.navigate(PantallasNav.PRINCIPAL.route) {
                        popUpTo(PantallasNav.LOGIN.route) { inclusive = true } // Evita volver a la pantalla de login
                    }
                }
                }
            }
}

