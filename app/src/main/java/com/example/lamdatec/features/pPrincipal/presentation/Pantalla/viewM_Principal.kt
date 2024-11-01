package com.example.lamdatec.features.pPrincipal.presentation.Pantalla

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class viewM_Principal : ViewModel() {
    private val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")
        private get

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

    val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))

    val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))

    init {
        MotoStatus()
        datosSensores()
    }

    private fun datosSensores() {

        database.child("/Sensores/VAR_CONTROL/FECHA_ACTUAL/").setValue(currentDate).addOnSuccessListener {
            database.child("/Sensores/VAR_CONTROL/HORA_ACTUAL/").setValue(currentTime).addOnSuccessListener {

            }.addOnFailureListener {

            }
        }

        database.child("/Sensores/SENSORES/MQ135/FECHAS/$currentDate/$currentTime/VSENSOR")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ppmAir.value = snapshot.getValue(Float::class.java) ?: 0f
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })

        //database.child("/Sensores/SENSORES/MQ2/FECHAS/$currentDate/$currentTime/VSENSOR")
        database.child("/Sensores/SENSORES/MQ2/Valor")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ppmCO2.value = snapshot.getValue(Float::class.java) ?: 0f
                }

                override fun onCancelled(error: DatabaseError) {}
            })

        database.child("/Sensores/SENSORES/MQ7/Valor")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    ppmHumedad.value = snapshot.getValue(Float::class.java) ?: 0f
                }

                override fun onCancelled(error: DatabaseError) {}
            })
    }

    private fun MotoStatus() {
        database.child("Sensores/Estatus").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                isMotoOn.value = snapshot.getValue(Boolean::class.java) ?: false
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private val _isButtonEnabled = MutableStateFlow(true)
    val isButtonEnabled: StateFlow<Boolean> get() = _isButtonEnabled

    fun CambiarMotoStatus() {
        isMotoOn.value = !isMotoOn.value
        database.child("Sensores/Estatus").setValue(isMotoOn.value)

        _isButtonEnabled.value = false
        viewModelScope.launch {
            delay(2000) // Espera el tiempo de la animación en milisegundos
            _isButtonEnabled.value = true // Reactiva el botón después de la animación
        }
    }
}
