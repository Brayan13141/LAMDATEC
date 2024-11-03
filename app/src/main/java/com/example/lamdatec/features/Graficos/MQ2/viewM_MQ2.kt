package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class viewM_MQ2(private val repository: SensorMQ2Repository = SensorMQ2Repository()) : ViewModel() {
    var puntosGrafico = MutableStateFlow<List<Point>>(emptyList())
        private set

    init {
        consultarDatosSensores()
    }

    private fun consultarDatosSensores() {
        repository.consultarDatosSensores { puntos ->
            puntosGrafico.value = puntos // Actualiza el estado de los puntos del gráfico
        }
    }
}
