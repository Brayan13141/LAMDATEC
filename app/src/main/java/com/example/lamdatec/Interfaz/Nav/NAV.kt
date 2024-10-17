package com.example.lamdatec.Interfaz.Nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lamdatec.Interfaz.Pantallas.PantallaConGrafico
import com.example.lamdatec.Interfaz.Pantallas.PantallaInicio

@Composable
fun App(
) {
        val navController = rememberNavController()
            NavHost(
                navController = navController,   // El NavController que estás usando
                startDestination = PantallasNav.PRINCIPAL.route   // La pantalla que será tu punto de inicio
            ) {
                composable( PantallasNav.PRINCIPAL.route) { PantallaInicio(navController) }
                composable( PantallasNav.S1.route) {  PantallaConGrafico(navController) }

            }
}

