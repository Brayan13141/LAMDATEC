package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class viewM_MQ2(private val repository: SensorMQ2Repository = SensorMQ2Repository()) : ViewModel() {
    var puntosGrafico = MutableStateFlow<List<Point>>(listOf())
        private set

    init {
        viewModelScope.launch {
            consultarDatosSensores()
        }
    }

    private suspend fun consultarDatosSensores() {
        repository.consultarDatosSensores { puntos ->
            puntosGrafico.value = puntos // Actualiza el estado de los puntos del gráfico
        }
    }
}
