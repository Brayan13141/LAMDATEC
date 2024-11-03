package com.example.lamdatec.features.Graficos.MQ2

import androidx.lifecycle.ViewModel
import com.google.firebase.database.*
import kotlinx.coroutines.flow.MutableStateFlow
import android.util.Log
import co.yml.charts.common.model.Point

class  viewM_MQ2 : ViewModel() {
    var puntosGrafico = MutableStateFlow<List<Point>>(emptyList())
        private set
   // val puntosGrafico: StateFlow<List<Point>> = puntosGrafico

    private val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")

    init {
        consultarDatosSensores()
        {  it ->
            puntosGrafico.value = it
        }
    }

    fun consultarDatosSensores(actualizar: (List<Point>) -> Unit) {
        var airValues : List<Float> = listOf()
        val database = FirebaseDatabase.getInstance().reference

        database.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").get().addOnSuccessListener { snapshot ->
            database.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val valor = snapshot.getValue(Int::class.java) ?: 0
                    airValues = airValues + valor.toFloat()
                    actualizar(airValues.mapIndexed { index, value ->
                        Point(index.toFloat(), value)
                    })
                    Log.e("Firebase", "Valor actualizado en Firebase: $valor")
                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
        }

    }
}
