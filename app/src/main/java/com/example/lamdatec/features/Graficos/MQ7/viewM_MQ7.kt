package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2RepositoryImp
import com.example.lamdatec.features.Graficos.MQ7.data.SensorMQ7Repository
import com.example.lamdatec.features.Graficos.MQ7.data.SensorMQ7RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class viewM_MQ7 @Inject constructor(
    private val repository: SensorMQ7RepositoryImp
) : ViewModel() {

    var puntosGrafico = MutableStateFlow<List<Point>>(emptyList())
        private set


    init {
        viewModelScope.launch {
            consultarDatosSensores()
        }
    }

    private fun consultarDatosSensores() {
        repository.consultarDatosSensores { puntos ->
            puntosGrafico.value = puntos // Actualiza el estado de los puntos del gráfico
        }
    }
}
