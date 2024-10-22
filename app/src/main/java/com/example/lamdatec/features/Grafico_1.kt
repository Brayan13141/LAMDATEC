package com.example.lamdatec.features

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lamdatec.features.components.PPantallas
import com.example.lamdatec.core.domain.model.ModeloSensores
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

@Composable
fun PantallaConGrafico(navController: NavHostController) {

    val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")

    PPantallas(navController,"SENSOR 1"){
        GraficoDatos()
        ConsultarDatosSensores(database)
        writeDataToFirebase()
    }
}

// Función para representar el gráfico
@Composable
fun GraficoDatos() {
    val datos : List<ModeloSensores> = listOf(
        ModeloSensores("Temperatura", 25.5f),
        ModeloSensores("Humedad", 60.0f),
        ModeloSensores("Presión", 1013.25f),
        )
    var puntos = ArrayList<LineChartData.Point>()
    datos.mapIndexed { index, modeloSensores ->
        puntos.add(LineChartData.Point(modeloSensores.value, modeloSensores.label))
    }

    var lineas = ArrayList<LineChartData>()
    lineas.add(LineChartData(points = puntos, lineDrawer = SolidLineDrawer()))

    LineChart(linesChartData = lineas,
        modifier = Modifier.padding(horizontal = 30.dp, vertical = 80.dp).height(300.dp))
}


@Composable
fun ConsultarDatosSensores(database: DatabaseReference) {
    // State para almacenar los valores de los sensores
    var air by remember { mutableStateOf("Loading...") }
    var co by remember { mutableStateOf("Loading...") }
    var h by remember { mutableStateOf("Loading...") }
    var status by remember { mutableStateOf("Loading...") }

    // Función para leer los datos de Firebase en tiempo real
    LaunchedEffect(Unit) {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Extrae los valores de los sensores
                air = snapshot.child("/Sensores/air").value.toString()
                co = snapshot.child("/Sensores/co").value.toString()
                h = snapshot.child("/Sensores/h").value.toString()
                status = snapshot.child("/Sensores/Estatus").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                // Maneja el error
                air = "Error"
                co = "Error"
                h = "Error"
                status = "Error"
            }
        })
    }

    // UI que muestra los valores de los sensores
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Estatus: $status")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Air Quality: $air")
        Spacer(modifier = Modifier.height(8.dp))
        Text("CO Level: $co")
        Spacer(modifier = Modifier.height(8.dp))
        Text("Humidity: $h")
    }
}


fun writeDataToFirebase() {
    // Obtén la referencia de la base de datos
    val database = FirebaseDatabase.getInstance().reference

    // Crea un mapa con los datos de ejemplo
    val data = mapOf(
        "status" to "OK",
        "air" to 100,
        "co" to 300,
        "h" to 500
    )

    // Guarda los datos bajo la ruta "Sensores"
    database.child("LAMDATEC/Sensores").setValue(data)
        .addOnSuccessListener {
            // Éxito al escribir los datos
            Log.d("Firebase", "Datos escritos exitosamente")
        }
        .addOnFailureListener { exception ->
            // Error al escribir los datos
            Log.e("Firebase", "Error al escribir en Firebase: ${exception.message}")
        }
}