package com.example.lamdatec.features.pPrincipal.data


import com.google.firebase.database.*
import kotlinx.coroutines.tasks.await
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Ppantalla_Repo_Fire {

    private val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")

    suspend fun setFechaHoraActual() {
        val currentDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        val currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
        database.child("/Sensores/VAR_CONTROL/FECHA_ACTUAL/").setValue(currentDate).await()
        database.child("/Sensores/VAR_CONTROL/HORA_ACTUAL/").setValue(currentTime).await()
    }

    fun obtenerValorSensor(ruta: String, callback: (Float) -> Unit) {

        database.child(ruta).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val valor = snapshot.child("Valor").getValue(Float::class.java) ?: 0f
                callback(valor)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    fun obtenerEstadoMoto(callback: (Boolean) -> Unit) {
        database.child("Sensores/Estatus").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val estado = snapshot.getValue(Boolean::class.java) ?: false
                callback(estado)
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    suspend fun cambiarEstadoMoto(estado: Boolean) {
        database.child("Sensores/Estatus").setValue(estado).await()
    }
}
