package com.example.lamdatec.Interfaz.Pantallas

import android.util.Log
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lamdatec.Interfaz.Pantallas.Plantilla.PPantallas
import com.example.lamdatec.R
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.random.Random


@Composable
fun PantallaConGrafico(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")
    var puntosGrafico by remember { mutableStateOf(listOf<LineChartData.Point>()) }
    var control by remember { mutableStateOf(1) }


    PPantallas(navController, stringResource(R.string.lblSensor1)) {
        // Llama a la función que actualiza el gráfico en base a los datos de Firebase
        ConsultarDatosSensores(database) { newPoints ->
            puntosGrafico = newPoints
        }

        // Llama a la función para incrementar el valor de 'air'
        writeDataToFirebaseIncrement(control)
        {  c ->
            control = c
        }


        // Dibuja el gráfico con los puntos actualizados
        GraficoDatos(puntosGrafico)

    }
}



@Composable
fun GraficoDatos(datos: List<LineChartData.Point>) {
    var puntos = ArrayList<LineChartData.Point>()
    datos.mapIndexed { index, modeloSensores ->
        puntos.add(LineChartData.Point(modeloSensores.value, modeloSensores.label))
    }

    var lineas = ArrayList<LineChartData>()
    lineas.add(LineChartData(points = puntos, lineDrawer = SolidLineDrawer()))

    LineChart(linesChartData = lineas,
        modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 80.dp)
            .height(300.dp))
}


// Función para consultar los datos del sensor en Firebase
@Composable
fun ConsultarDatosSensores(database: DatabaseReference, onPointsUpdate: (List<LineChartData.Point>) -> Unit) {
    var airValues by remember { mutableStateOf(listOf<Float>()) }  // Lista para almacenar los valores de 'air'

    // Función para leer los datos de Firebase en tiempo real
    LaunchedEffect(Unit) {
        database.child("/Sensores").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Extrae los valores de 'air' y los agrega a la lista
                val air = snapshot.child("/air").getValue(Float::class.java) ?: 0f

                // Actualiza la lista de valores con el nuevo valor de 'air'
                airValues = airValues + air

                // Crea una lista de puntos para el gráfico
                val puntos = airValues.mapIndexed { index, value ->
                    LineChartData.Point(value, "$index")
                }

                // Actualiza los puntos del gráfico
                onPointsUpdate(puntos)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error al leer los datos: ${error.message}")
            }
        })
    }
}

fun writeDataToFirebaseIncrement(control: Int,c: (Int) -> Unit) {
    val database = FirebaseDatabase.getInstance().reference

    // Genera un valor aleatorio entre 1 y 1000
    val newAirValue = Random.nextInt(1, 1001)

    // Lee el valor actual de 'air' y lo incrementa
    database.child("LAMDATEC/Sensores/air").get().addOnSuccessListener { snapshot ->

        // Aplica el filtro para que el valor no supere 200
        if (control <= 10) {

            // Actualiza el valor en Firebase
            database.child("LAMDATEC/Sensores/air").setValue(newAirValue)

                .addOnSuccessListener {
                    Log.d("Firebase", "Valor de 'air' actualizado a: $newAirValue")
                }
                .addOnFailureListener { exception ->
                    Log.e("Firebase", "Error al actualizar 'air': ${exception.message}")
                }
            c(control+1)
        } else {
            Log.d("Firebase", "El valor de 'air' no se puede incrementar porque excede 200.")
        }
    }.addOnFailureListener { exception ->
        Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
    }
}
