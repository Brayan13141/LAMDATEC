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
        dia: String? = null,
        actualizar: (Pair<List<Point>, Float>) -> Unit
    )
    fun obtenerFechasDisponibles(actualizar: (List<String>) -> Unit)
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
                filtro: FiltrosFecha, Fecha: String?, actualizar: (Pair<List<Point>, Float>) -> Unit
    ) {
        val ref = db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/FECHAS")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val puntos = mutableListOf<Point>()
                var valorActual = 0f

                //-------------------------------- Obtener la fecha actual y aplicar el filtro PARA DIA ACTUAL--------------------------------
                val calendar = Calendar.getInstance()
                val formatoFecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val fechaActualFormateada = formatoFecha.format(calendar.time)

                for (fechaSnapshot in snapshot.children) {
                    val fecha = fechaSnapshot.key ?: continue//REGISTO_FECHA_FIREBASE
                    val fechaActualDate: Date = formatoFecha.parse(fechaActualFormateada) ?: Date()
                    val fechaFirebaseDate: Date = formatoFecha.parse(fecha) ?: Date()

                //--------------------------------FILTRO PARA LOS FILTROS--------------------------------
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
                    //-------------------------------- DATOS DE LA BD DIA ACTUAL--------------------------------
                    if (incluirFecha.first==true && incluirFecha.second == "Dia" && Fecha == "") {
                        for (horaSnapshot in fechaSnapshot.children) {
                           //Log.e("REPOMQ2-FECHAS", "fechaSnapshot : ${fechaSnapshot}")
                            //SE FILTRAN LOS DATOS EN BASE A LA FECHA ACTUAL(SNAPSHOT) Y LA FORMATEADA(DIAACTUAL)
                            if (fechaSnapshot.key == fechaActualFormateada)
                            {
                                //PARA CADA REGISTRO DENTRO DE LA FECHA ACTUAL
                                for(Valor in horaSnapshot.children)
                                {
                                    val hora = horaSnapshot.key ?: continue
                                    val valor = horaSnapshot.child("VSENSOR").getValue(Float::class.java) ?: 0f
                                   //Log.e("REPOMQ2", "HORA BD : $hora- VALOR: $valor ")
                                    puntos.add(Point(puntos.size.toFloat(), valor))
                                    valorActual = valor // Actualiza al último valor encontrado
                                }

                            }
                        }
                        //-------------------------------- DATOS DE LA BD DIA SELECCIONADO--------------------------------
                    }
                    else if (incluirFecha.first==true && incluirFecha.second == "Dia" && Fecha != "") {
                          for (horaSnapshot in fechaSnapshot.children) {
                              //Log.e("REPOMQ2-FECHAS", "fechaSnapshot : ${fechaSnapshot}")
                              //Log.e("REPOMQ2-FECHAS", "fechaCREADA : $Fecha")
                              //Log.e("REPOMQ2-FECHAS", "KEY : ${fechaSnapshot.key}")
                              if (fechaSnapshot.key == Fecha)
                            {
                                for(Valor in horaSnapshot.children)
                                {
                                    val hora = horaSnapshot.key ?: continue
                                    val valor = horaSnapshot.child("VSENSOR").getValue(Float::class.java) ?: 0f
                                    Log.e("REPOMQ2", "HORA BD : $hora- VALOR: $valor ")
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
    override fun obtenerFechasDisponibles(actualizar: (List<String>) -> Unit) {
        val ref = db.reference.child("LAMDATEC/Sensores/SENSORES/MQ2/FECHAS")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val fechas = snapshot.children.mapNotNull { it.key }
                actualizar(fechas)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al obtener datos: ${error.message}")
            }
        })
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

