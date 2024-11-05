package com.example.lamdatec.features.Graficos.MQ2.data

import android.util.Log
import com.google.firebase.database.*
import co.yml.charts.common.model.Point
import javax.inject.Inject
import javax.inject.Singleton

interface SensorMQ2Repository {
    fun consultarDatosSensores(actualizar: (List<Point>) -> Unit)
}

@Singleton
class SensorMQ2RepositoryImp @Inject constructor(
    private val db: FirebaseDatabase
) : SensorMQ2Repository {

    override fun consultarDatosSensores(actualizar: (List<Point>) -> Unit) {
        var airValues: List<Float> = listOf()

        // Acceso a la referencia de la base de datos
        // listener para conexion exitosa
        db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").get().addOnSuccessListener {
            // listener para obtener los valores
            db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor")
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val valor = snapshot.getValue(Int::class.java) ?: 0
                        airValues = airValues + valor.toFloat()
                        actualizar(airValues.mapIndexed { index, value ->
                            Point(index.toFloat(), value)
                        })
                        Log.e("Firebase", "Valor actualizado en Firebase: $valor")
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Manejo del error
                    }
                })
            // listener para fallo en conexion
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
        }
    }
}
