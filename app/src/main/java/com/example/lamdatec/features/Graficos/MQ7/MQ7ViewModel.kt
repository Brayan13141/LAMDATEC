package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ7.data.SensorMQ7Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MQ7ViewModel @Inject constructor(
    private val repository: SensorMQ7Repository
) : ViewModel() {
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