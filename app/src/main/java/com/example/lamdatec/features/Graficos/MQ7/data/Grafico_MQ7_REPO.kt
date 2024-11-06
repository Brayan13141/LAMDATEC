package com.example.lamdatec.features.Graficos.MQ7.data

import android.util.Log
import com.google.firebase.database.*
import co.yml.charts.common.model.Point
import dagger.hilt.android.scopes.ViewModelScoped
import jakarta.inject.Inject
import jakarta.inject.Singleton
import kotlinx.coroutines.tasks.await

interface SensorMQ7Repository {
    fun consultarDatosSensores(actualizar: (List<Point>) -> Unit)
}

@ViewModelScoped
class SensorMQ7RepositoryImp @Inject constructor(
    private val db: FirebaseDatabase
) : SensorMQ7Repository {
    override fun consultarDatosSensores(actualizar: (List<Point>) -> Unit) {
        var airValues : List<Float> = listOf()
        val database = db.reference

        database.child("LAMDATEC/Sensores/SENSORES/MQ7/Valor").get().addOnSuccessListener { snapshot ->
            database.child("LAMDATEC/Sensores/SENSORES/MQ7/Valor").addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    val valor = snapshot.getValue(Int::class.java) ?: 0
                    airValues = airValues + valor.toFloat()
                    actualizar(airValues.mapIndexed { index, value ->
                        Point(index.toFloat(), value)
                    })
                    //Log.e("Firebase", "Valor actualizado en Firebase: $valor")
                }
                override fun onCancelled(error: DatabaseError) {
              //
                }
            })
        }.addOnFailureListener { exception ->
            Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
        }

    }
}
