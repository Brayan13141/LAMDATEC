package com.example.lamdatec.features.pPrincipal.presentation.Pantalla

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lamdatec.features.pPrincipal.data.Ppantalla_Repo_Fire
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class viewM_Principal(private val repository: Ppantalla_Repo_Fire = Ppantalla_Repo_Fire()) : ViewModel() {

    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    // Estados para los valores de los sensores
    var ppmAir = mutableStateOf(0f)
        private set
    var ppmCO2 = mutableStateOf(0f)
        private set
    var ppmHumedad = mutableStateOf(0f)
        private set

    // Estado para el estado de la moto
    var isMotoOn = mutableStateOf(false)
        private set
    var ControlMotoOn = mutableStateOf(false)
        private set

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    init {
        viewModelScope.launch {
            repository.setFechaHoraActual()
        }
        obtenerDatosSensores()
        obtenerEstadoMoto()
    }

    private fun obtenerDatosSensores() {
        /*repository.obtenerValorSensor("/Sensores/SENSORES/MQ135/FECHAS/${currentDate}/${currentTime}/VSENSOR") { value ->
            ppmAir.value = value
        }*/

        repository.obtenerValorSensor("/Sensores/SENSORES/MQ2/") { value ->
            ppmCO2.value = value
        }

        repository.obtenerValorSensor("/Sensores/SENSORES/MQ7/") { value ->
            ppmHumedad.value = value
        }
    }

    private fun obtenerEstadoMoto() {
        repository.obtenerEstadoMoto { estado ->
            isMotoOn.value = estado
        }
    }

    fun CambiarMotoStatus() {
        isMotoOn.value = !isMotoOn.value
        viewModelScope.launch {
            repository.cambiarEstadoMoto(isMotoOn.value)
            _isButtonEnabled.value = false
            delay(2000) // Espera el tiempo de la animación
            _isButtonEnabled.value = true
        }
    }
}



