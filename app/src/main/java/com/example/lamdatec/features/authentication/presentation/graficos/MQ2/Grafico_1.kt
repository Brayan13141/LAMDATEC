package com.example.lamdatec.Interfaz.Pantallas

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import co.yml.charts.axis.AxisData
import co.yml.charts.common.model.Point
import co.yml.charts.ui.linechart.LineChart
import co.yml.charts.ui.linechart.model.GridLines
import co.yml.charts.ui.linechart.model.IntersectionPoint
import co.yml.charts.ui.linechart.model.LineChartData
import co.yml.charts.ui.linechart.model.LinePlotData
import co.yml.charts.ui.linechart.model.LineStyle
import co.yml.charts.ui.linechart.model.LineType
import co.yml.charts.ui.linechart.model.SelectionHighlightPoint
import co.yml.charts.ui.linechart.model.SelectionHighlightPopUp
import co.yml.charts.ui.linechart.model.ShadowUnderLine
import com.example.lamdatec.R
import com.example.lamdatec.features.components.PPantallas
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lamdatec.features.authentication.presentation.graficos.MQ2.viewM_MQ2

@Composable
fun PantallaConGrafico(navController: NavHostController, viewModel: viewM_MQ2 = viewModel()) {
    val puntosGrafico by viewModel.puntosGrafico.collectAsState()

    PPantallas(navController, stringResource(R.string.lblSensor1)) {
        Column(modifier = Modifier.padding(2.dp)) {
            Botones()

            if (puntosGrafico.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(BorderStroke(2.dp, Color.Black))
                ) {
                    if (puntosGrafico.isNotEmpty())
                    Grafico(viewModel)
                }
            }
            ValorCard("NADA", "AUN")
        }
    }
}


@Composable
fun ValorCard(valor: String, descripcion: String) {
    Card(
        shape = RoundedCornerShape(8.dp), // Esquinas redondeadas
        elevation = CardDefaults.elevatedCardElevation(), // Sombra
        modifier = Modifier
            .size(width = 80.dp, height = 100.dp) // Tamaño del elemento
            .padding(8.dp) // Espacio alrededor de la tarjeta
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = valor,
                color = Color(0xFF4CAF50), // Color verde
                style = MaterialTheme.typography.bodyMedium, // Tamaño grande para el valor
                fontWeight = FontWeight.Bold
            )
            Text(
                text = descripcion,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium // Tamaño pequeño para la descripción
            )
        }
    }
}

@Composable
fun Botones()
{
    Column(modifier = Modifier.padding(16.dp)) {
        // Filas de Fechas
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fechas = listOf(
                "01 OCT", "02 OCT", "03 OCT", "04 OCT", "05 OCT", "06 OCT",
                "07 OCT", "08 OCT", "09 OCT", "10 OCT"
            ) // Agrega más fechas si necesitas
            items(fechas.size-1) { fecha ->
                Card(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier.padding(4.dp).width(60.dp)
                ) {
                    Text(
                        text = fechas.get(fecha).toString(),
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }
        // Botones de selección debajo
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val opciones = listOf("Por día", "Por semana", "Por mes", "Rango")
            opciones.forEach { opcion ->
                Button(
                    onClick = { /* Acción para cada botón */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (opcion == "Por día") Color.Black else Color.LightGray
                    ),
                    modifier = Modifier.padding(4.dp)
                ) {
                    Text(
                        text = opcion,
                        color = if (opcion == "Por día") Color.White else Color.Black
                    )
                }
            }
        }
    }
}


@Composable
fun Grafico(viewModel: viewM_MQ2)
{
    val puntosGrafico by viewModel.puntosGrafico.collectAsState()

    val yAxisMaxValue = puntosGrafico.maxOfOrNull { it.y.toInt() } ?: 0
    val steps = 10  // Número de pasos o divisiones en el eje Y
    val yAxisStepSize = yAxisMaxValue / steps // Tamaño de cada paso en el eje Y

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .backgroundColor(color = Color.White)
        .steps(10)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(10.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(10)
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(10.dp)
        .labelData { i ->
            (i * yAxisStepSize).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                    co.yml.charts.ui.linechart.model.Line(
                        dataPoints =  puntosGrafico,
                        LineStyle(color = MaterialTheme.colorScheme.primary,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(MaterialTheme.colorScheme.inversePrimary,
                                Color.Transparent))
                    ),
                    SelectionHighlightPopUp()
                )
            ),
        ),
        isZoomAllowed = true,
        paddingRight = 3.dp,
        xAxisData = xAxisData,
        yAxisData = yAxisData,
        gridLines = GridLines(),
        backgroundColor = Color.White
    )
    LineChart(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )

}

