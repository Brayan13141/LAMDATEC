package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.lamdatec.Interfaz.Pantallas.Plantilla.PPantallas


@Composable
fun PantallaInicio(navController: NavHostController) {
    PPantallas(navController,"BIENVENIDO A LAMDATEC")
    {

    }
}