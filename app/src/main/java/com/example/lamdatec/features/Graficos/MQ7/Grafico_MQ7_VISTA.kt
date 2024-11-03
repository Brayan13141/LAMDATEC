package com.example.lamdatec.features.Graficos.MQ7

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lamdatec.features.Graficos.MQ2.viewM_MQ7
import com.example.lamdatec.features.components.PantallaConGraficoGENERAL

@Composable
fun GraficoMQ7_VISTA(navController: NavHostController, viewModel: viewM_MQ7 = viewModel()) {

    val puntosGrafico by viewModel.puntosGrafico.collectAsState()

    PantallaConGraficoGENERAL(navController,"MQ7",puntosGrafico)
}

