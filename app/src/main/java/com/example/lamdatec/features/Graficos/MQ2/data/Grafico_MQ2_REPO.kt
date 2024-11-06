package com.example.lamdatec.features.Graficos.MQ2.data

import android.util.Log
import com.google.firebase.database.*
import co.yml.charts.common.model.Point
import javax.inject.Inject
import javax.inject.Singleton

interface SensorMQ2Repository {
    fun consultarDatosSensores(actualizar: (Pair<List<Point>, Float>) -> Unit)
}

@Singleton
class SensorMQ2RepositoryImp @Inject constructor(
    private val db: FirebaseDatabase
) : SensorMQ2Repository {
    override fun consultarDatosSensores(actualizar: (Pair<List<Point>, Float>) -> Unit) {
        var airValues: List<Float> = listOf()

        // Acceso a la referencia de la base de datos
        // Listener para conexión exitosa
        db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").get().addOnSuccessListener {
            // Listener para obtener los valores en tiempo real
            db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val valor = snapshot.getValue(Int::class.java) ?: 0
                        airValues = airValues + valor.toFloat()

                        // Convertimos airValues a una lista de puntos y lo enviamos junto con valor
                        val puntos = airValues.mapIndexed { index, value ->
                            Point(index.toFloat(), value)
                        }
                        actualizar(Pair(puntos, valor.toFloat()))

                        Log.e("Firebase", "Valor actualizado en Firebase: $valor")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Manejo del error
                        Log.e("Firebase", "Error al obtener datos: ${error.message}")
                    }
                })
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
        }
    }
}
