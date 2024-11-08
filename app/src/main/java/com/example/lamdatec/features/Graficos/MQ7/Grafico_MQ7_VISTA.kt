package com.example.lamdatec.features.Graficos.MQ7

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.lamdatec.features.Graficos.MQ2.MQ7ViewModel
import com.example.lamdatec.features.components.PantallaConGraficoGENERAL

@Composable
fun GraficoMQ7_VISTA(
    navController: NavHostController,
    viewModel: MQ7ViewModel = hiltViewModel()
) {

    val puntosGrafico by viewModel.puntosGrafico.collectAsState()

    PantallaConGraficoGENERAL(
        navController = navController,
        puntosGrafico = puntosGrafico,
        valor = 0
    )
}

