package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.yml.charts.common.model.Point
import com.example.lamdatec.features.Graficos.MQ2.data.FiltrosFecha
import com.example.lamdatec.features.Graficos.MQ2.data.SensorMQ2RepositoryImp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MQ2ViewModel @Inject constructor(
    private val repository: SensorMQ2RepositoryImp
) : ViewModel() {
    //-------------------------------------TIEMPO REAL-----------------------------
    var puntosGrafico = MutableStateFlow<List<Point>>(emptyList())
        private set
    var valorActual = MutableStateFlow<Float>(0f)
        private set

    //-------------------------------VALORES PARA FILTRAR-------------------------------------------------------
    var valorActualFiltro = MutableStateFlow<FiltrosFecha>(FiltrosFecha.NINGUNO)
        private set
    var valorActualFiltrosFecha = MutableStateFlow<String>("")
        private set

    //-------------------------------------------FECHAS BOTONES---------------------------------

    var Fechas = MutableStateFlow<List<String>>(emptyList())
        private set

    //-----------------------------------------FUNCIONES-------------------------------------------------
    fun limpiarPuntos() {
        puntosGrafico.value = emptyList()
    }

    init {
        if (valorActualFiltro.value == FiltrosFecha.NINGUNO) {
            consultarDatosSensores()
            obtenerFechasDisponibles()
        }
    }

    fun consultarDatosSensores() {
        viewModelScope.launch {
            repository.consultarDatosSensores { (puntos, valor) ->
                if (valorActualFiltro.value == FiltrosFecha.NINGUNO) {
                    puntosGrafico.value =
                        puntos       // Actualiza el estado de los puntos del gráfico
                    valorActual.value = valor          // Actualiza el estado del valor actual
                }
            }
        }
    }

    fun consultarDatosConFiltro(filtro: FiltrosFecha, fecha: String? = null) {
        viewModelScope.launch {
            valorActualFiltro.value = filtro
            repository.consultarDatosSensoresConFiltro(filtro, Fecha = fecha) { (puntos, valor) ->
                if (valorActualFiltro.value != FiltrosFecha.NINGUNO) {
                    puntosGrafico.value =
                        puntos       // Actualiza el estado de los puntos del gráfico
                    valorActual.value = valor          // Actualiza el estado del valor actual
                }

            }
        }

    }

    fun Actualizarfiltros(filtro: FiltrosFecha, fecha: String? = null) {
        valorActualFiltro.value = filtro
        fecha?.let {
            valorActualFiltrosFecha.value = fecha
        }
    }

    fun obtenerFechasDisponibles() {
        viewModelScope.launch {
            repository.obtenerFechasDisponibles { Fechas1->
              Fechas.value = Fechas1
            }
        }
    }
}
