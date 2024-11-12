package com.example.lamdatec.Interfaz.Pantallas

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.MQ2ViewModel
import com.example.lamdatec.features.Graficos.MQ2.data.FiltrosFecha
import com.example.lamdatec.features.components.PantallaConGraficoGENERAL

@Composable
fun GraficoMQ2_VISTA(
    navController: NavHostController,
    viewModel: MQ2ViewModel = hiltViewModel()
) {
    val puntosGrafico = viewModel.puntosGrafico.collectAsState()
    val valorActual = viewModel.valorActual.collectAsState()
     val Fechas = viewModel.Fechas.collectAsState()

    PantallaConGraficoGENERAL(navController, "MQ2", puntosGrafico.value,valorActual.value.toInt(),
    fechas = Fechas.value)
    {
        viewModel.Actualizarfiltros(it.first,it.second?.toString())
        viewModel.obtenerFechasDisponibles()
        if (viewModel.valorActualFiltro.value != FiltrosFecha.NINGUNO)
        {
            viewModel.limpiarPuntos()
            Log.e("filtro-PG",it.first.toString() +" "+it.second.toString())
            viewModel.consultarDatosConFiltro(filtro = viewModel.valorActualFiltro.value,fecha = viewModel.valorActualFiltrosFecha.value)
        }else{
            viewModel.limpiarPuntos()
            viewModel.consultarDatosSensores()
        }

    }

}

