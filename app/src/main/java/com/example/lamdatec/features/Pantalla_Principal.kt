package com.example.lamdatec.features

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.example.lamdatec.features.components.PPantallas


@Composable
fun PantallaInicio(navController: NavHostController) {
    PPantallas(navController,"BIENVENIDO A LAMDATEC")
    {

    }
}