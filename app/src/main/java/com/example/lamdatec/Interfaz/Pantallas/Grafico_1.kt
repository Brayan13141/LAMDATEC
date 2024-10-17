package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavHostController
import com.example.lamdatec.Modelos.ModeloSensores
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import kotlinx.coroutines.launch

@Composable
fun PantallaConGrafico(navController: NavHostController) {
    PPantallas(navController,"SENSOR 1"){
        GraficoDatos()
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
