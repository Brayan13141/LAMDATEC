package com.example.lamdatec.features.components

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

@Composable
fun PantallaConGraficoGENERAL(
    navController: NavHostController,
    titulo: String,
    puntosGrafico: List<Point>
) {
    PPantallas(navController, titulo) {
        Column {
            Botones()
            if (puntosGrafico.isNotEmpty()) {
                Box(modifier = Modifier.fillMaxWidth()
                    .border(1.dp, Color.Black, shape = MaterialTheme.shapes.large)
                ){
                    Grafico(puntosGrafico)
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
        modifier =  Modifier
            .size(width = 80.dp, height = 100.dp) // Tamaño del elemento
            .padding(8.dp) // Espacio alrededor de la tarjeta
    ) {
        Column(
            modifier =  Modifier.padding(8.dp),
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
fun Botones() {
    Column(modifier =Modifier.padding(16.dp)) {
        // Filas de Fechas
        LazyRow(
            modifier =  Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fechas = listOf(
                "01 OCT", "02 OCT", "03 OCT", "04 OCT", "05 OCT", "06 OCT",
                "07 OCT", "08 OCT", "09 OCT", "10 OCT"
            ) // Agrega más fechas si necesitas
            items(fechas.size - 1) { fecha ->
                Card(
                    shape = MaterialTheme.shapes.small,
                    modifier =  Modifier
                        .padding(4.dp)
                        .width(60.dp)
                ) {
                    Text(
                        text = fechas[fecha],
                        color = Color.White,
                        modifier = Modifier.padding(
                            vertical = 8.dp,
                            horizontal = 12.dp
                        )
                    )
                }
            }
        }
        // Botones de selección debajo
        Row(
            modifier =Modifier
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
                    modifier =  Modifier.padding(4.dp)
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
fun Grafico(Puntos: List<Point>) {
    val steps = 20
    val puntosGrafico =
        remember { mutableStateListOf<Point>().apply { addAll(Puntos) } }

    if (puntosGrafico.isNotEmpty()) {
        // Actualizar puntos gráficos cuando `Puntos` cambia
        LaunchedEffect(Puntos) {
            puntosGrafico.clear()
            puntosGrafico.addAll(Puntos.takeLast(10)) // Mantener máximo 20 puntos
        }

        // Valor máximo y mínimo del eje Y basado en los valores actuales de los puntos
        val yAxisMaxValue by remember {
            derivedStateOf { puntosGrafico.maxOfOrNull { it.y.toInt() } ?: 0 }
        }
        val yAxisMinValue by remember {
            derivedStateOf { puntosGrafico.minOfOrNull { it.y.toInt() } ?: 0 }
        }


        val xAxisData = AxisData.Builder()
            .axisStepSize(30.dp)
            .backgroundColor(color = Color.Black)
            .steps(10)
            .labelData { i -> i.toString() }
            .labelAndAxisLinePadding(10.dp)
            .axisLabelColor(Color.White)
            .axisLabelDescription { "PASOS" }
            .build()

        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.Black)
            .labelAndAxisLinePadding(20.dp)
            .axisLabelColor(Color.White)
            .labelData { i ->
                // Dividimos el rango exacto en 10 partes y mostramos los valores correspondientes
                val range = yAxisMaxValue - yAxisMinValue
                val exactValue = yAxisMinValue + (i * range / steps)
                exactValue.toString()
            }.build()

        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    co.yml.charts.ui.linechart.model.Line(
                        dataPoints = puntosGrafico.map {
                            Point(
                                it.x,
                                it.y
                            )
                        },
                        LineStyle(
                            color = MaterialTheme.colorScheme.primary,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                        IntersectionPoint(
                            color = Color.White
                        ),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(
                            alpha = 0.8f,
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    MaterialTheme.colorScheme.inversePrimary,
                                    Color.Transparent
                                )
                            )
                        ),
                        SelectionHighlightPopUp()
                    )
                ),
            ),
            isZoomAllowed = true,
            paddingRight = 5.dp,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.Black
        )
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            lineChartData = lineChartData
        )
    }
}

