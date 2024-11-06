package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MQ2ViewModel @Inject constructor(
    private val repository: SensorMQ2RepositoryImp
) : ViewModel() {

    var puntosGrafico = MutableStateFlow<List<Point>>(emptyList())
        private set

    var valorActual = MutableStateFlow<Float>(0f)
        private set

    init {
        consultarDatosSensores()
    }

    private fun consultarDatosSensores() {
        repository.consultarDatosSensores { (puntos, valor) ->
            puntosGrafico.value = puntos       // Actualiza el estado de los puntos del gráfico
            valorActual.value = valor          // Actualiza el estado del valor actual
        }
    }
}
