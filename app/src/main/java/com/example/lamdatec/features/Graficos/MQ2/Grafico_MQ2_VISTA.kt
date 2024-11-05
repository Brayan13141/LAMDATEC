package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController

import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lamdatec.features.Graficos.MQ2.MQ2ViewModel
import com.example.lamdatec.features.components.PantallaConGraficoGENERAL

@Composable
fun GraficoMQ2_VISTA(
    navController: NavHostController,
    viewModel: MQ2ViewModel = hiltViewModel()
) {
    val puntosGrafico by viewModel.puntosGrafico.collectAsState()
    PantallaConGraficoGENERAL(navController,"MQ2",puntosGrafico)
}

