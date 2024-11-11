package com.example.lamdatec.features.Graficos.MQ2.data

import android.util.Log
import com.google.firebase.database.*
import co.yml.charts.common.model.Point
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

interface SensorMQ2Repository {
    fun consultarDatosSensores(actualizar: (Pair<List<Point>, Float>) -> Unit)
    fun consultarDatosSensoresConFiltro(
        filtro: FiltrosFecha,
        dia: Int? = null,
        actualizar: (Pair<List<Point>, Float>) -> Unit
    )
    fun obtenerFechasDisponibles(actualizar: (Pair<List<String>, List<String>>) -> Unit)
}

@Singleton
class SensorMQ2RepositoryImp @Inject constructor(
    private val db: FirebaseDatabase
) : SensorMQ2Repository {
    //DATOS DEL SENSOR EN TIEMPO REAL
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

// DATOS DEL SENSOR CON FILTROS
    override fun consultarDatosSensoresConFiltro(
                filtro: FiltrosFecha, dia: Int?, actualizar: (Pair<List<Point>, Float>) -> Unit
    ) {
        val ref = db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/FECHAS")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val puntos = mutableListOf<Point>()
                var valorActual = 0f

                // Obtener la fecha actual y aplicar el filtro
                val calendar = Calendar.getInstance()
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fechaFormateada = formatoFecha.format(calendar.time)

                for (fechaSnapshot in snapshot.children) {
                    val fecha = fechaSnapshot.key ?: continue
                    val fechaActualDate: Date = formatoFecha.parse(fechaFormateada) ?: Date()
                    val fechaFirebaseDate: Date = formatoFecha.parse(fecha) ?: Date()

                    val incluirFecha = when(filtro) {
                        FiltrosFecha.DIA -> {
                            val incluirFecha = isSameDay(calendar.time, fechaActualDate)
                            Pair(incluirFecha,"Dia")
                        }
                        FiltrosFecha.SEMANA -> {
                            val incluirFecha = isWithinLastWeek(fechaFirebaseDate)
                            Pair(incluirFecha, "Semana")
                        }

                        FiltrosFecha.MES -> {
                            val incluirFecha = isWithinLastMonth(fechaFirebaseDate)
                            Pair(incluirFecha, "Mes")
                        }
                        FiltrosFecha.NINGUNO -> {
                            val incluirFecha =false
                            Pair(incluirFecha, "Ninguno")
                        }
                    }
                    if (incluirFecha.first==true && incluirFecha.second == "Dia") {
                        for (horaSnapshot in fechaSnapshot.children) {
                          //  Log.e("REPOMQ2", "fechaSnapshot : ${fechaSnapshot}")
                            if (fechaSnapshot.key == fechaFormateada)
                            {
                                for(Valor in horaSnapshot.children)
                                {
                                    val hora = horaSnapshot.key ?: continue
                                    val valor = horaSnapshot.child("VSENSOR").getValue(Float::class.java) ?: 0f
                                   // Log.e("REPOMQ2", "HORA BD : $hora- VALOR: $valor ")
                                    puntos.add(Point(puntos.size.toFloat(), valor))
                                    valorActual = valor // Actualiza al último valor encontrado
                                }

                            }

                        }
                    }
                }

                actualizar(Pair(puntos, valorActual))
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al filtrar datos: ${error.message}")
            }
        })
    }
// FECHAS PARA LOS BOTONES
override fun obtenerFechasDisponibles(actualizar: (Pair<List<String>,List<String>>) -> Unit) {
    var Dias: List<String> = listOf()
    var Mes: List<String> = listOf()
    // Acceso a la referencia de la base de datos
    db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/FECHAS").get().addOnSuccessListener {
        db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/FECHAS")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val dataMap = snapshot.getValue(object : GenericTypeIndicator<Map<String, Any>>() {}) ?: emptyMap()
                    // Iterar sobre las fechas en el mapa
                    for ((key, value) in dataMap) {
                        // Convierte la clave de fecha completa en un formato de día y mes
                        val dateParts = key.toString().split("-") // Asumiendo formato "YYYY-MM-DD"
                        if (dateParts.size == 3) {
                            val day = dateParts[2] // Día
                            val month = dateParts[1]// getMonthName(dateParts[1].toInt()) // Mes
                            Dias = Dias + day
                            Mes = Mes + month
                        }
                    }
                    actualizar(Pair(Dias, Mes))
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e("Firebase", "Error al obtener datos: ${error.message}")
                }
            })
    }.addOnFailureListener { exception ->
        Log.e("Firebase", "Error al leer los valores: ${exception.message}")
    }
}







    // Función para verificar si es el mismo día
    private fun isSameDay(fecha1: Date, fecha2: Date): Boolean {
        val cal1 = Calendar.getInstance().apply { time = fecha1 }
        val cal2 = Calendar.getInstance().apply { time = fecha2 }

        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
                cal1.get(Calendar.DAY_OF_YEAR) == cal2.get(Calendar.DAY_OF_YEAR)
    }
     //FALTAN VERIFICAR
    // Función para verificar si está dentro de la última semana
    private fun isWithinLastWeek(date: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.add(Calendar.DAY_OF_YEAR, -7)
        return date.after(cal.time)
    }

    // Función para verificar si está dentro del último mes
    private fun isWithinLastMonth(date: Date): Boolean {
        val cal = Calendar.getInstance()
        cal.add(Calendar.MONTH, -1)
        return date.after(cal.time)
    }
}

