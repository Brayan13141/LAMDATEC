package com.example.lamdatec.features.components

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
import androidx.compose.ui.Alignment
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
        Column() {
            Botones()

            if (puntosGrafico.isNotEmpty()) {
                Box() {
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
        modifier = androidx.compose.ui.Modifier
            .size(width = 80.dp, height = 100.dp) // Tamaño del elemento
            .padding(8.dp) // Espacio alrededor de la tarjeta
    ) {
        Column(
            modifier = androidx.compose.ui.Modifier.padding(8.dp),
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
                color = androidx.compose.ui.graphics.Color.Gray,
                style = MaterialTheme.typography.bodyMedium // Tamaño pequeño para la descripción
            )
        }
    }
}

@Composable
fun Botones()
{
    Column(modifier = androidx.compose.ui.Modifier.padding(16.dp)) {
        // Filas de Fechas
        LazyRow(
            modifier = androidx.compose.ui.Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fechas = listOf(
                "01 OCT", "02 OCT", "03 OCT", "04 OCT", "05 OCT", "06 OCT",
                "07 OCT", "08 OCT", "09 OCT", "10 OCT"
            ) // Agrega más fechas si necesitas
            items(fechas.size-1) { fecha ->
                Card(
                    shape = MaterialTheme.shapes.small,
                    modifier = androidx.compose.ui.Modifier.padding(4.dp).width(60.dp)
                ) {
                    Text(
                        text = fechas.get(fecha).toString(),
                        color = androidx.compose.ui.graphics.Color.White,
                        modifier = androidx.compose.ui.Modifier.padding(vertical = 8.dp, horizontal = 12.dp)
                    )
                }
            }
        }
        // Botones de selección debajo
        Row(
            modifier = androidx.compose.ui.Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val opciones = listOf("Por día", "Por semana", "Por mes", "Rango")
            opciones.forEach { opcion ->
                Button(
                    onClick = { /* Acción para cada botón */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (opcion == "Por día") androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.LightGray
                    ),
                    modifier = androidx.compose.ui.Modifier.padding(4.dp)
                ) {
                    Text(
                        text = opcion,
                        color = if (opcion == "Por día") androidx.compose.ui.graphics.Color.White else androidx.compose.ui.graphics.Color.Black
                    )
                }
            }
        }
    }
}


@Composable
fun Grafico(Puntos: List<Point>)
{
    val puntosGrafico = Puntos

    val yAxisMaxValue = puntosGrafico.maxOfOrNull { it.y.toInt() } ?: 0
    val steps = 15  // Número de pasos o divisiones en el eje Y
    val yAxisStepSize = yAxisMaxValue / steps // Tamaño de cada paso en el eje Y

    val xAxisData = AxisData.Builder()
        .axisStepSize(40.dp)
        .backgroundColor(color = androidx.compose.ui.graphics.Color.White)
        .steps(10)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(10.dp)
        .build()

    val yAxisData = AxisData.Builder()
        .steps(10)
        .backgroundColor(androidx.compose.ui.graphics.Color.White)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            (steps * 100).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                co.yml.charts.ui.linechart.model.Line(
                    dataPoints =  puntosGrafico.map { co.yml.charts.common.model.Point(it.x.toFloat(), it.y.toFloat()) },
                    LineStyle(color = MaterialTheme.colorScheme.primary,
                        lineType = LineType.SmoothCurve(isDotted = false)
                    ),
                    IntersectionPoint(),
                    SelectionHighlightPoint(),
                    ShadowUnderLine(
                        alpha = 0.5f,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.inversePrimary,
                                androidx.compose.ui.graphics.Color.Transparent))
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
        backgroundColor = androidx.compose.ui.graphics.Color.White
    )
    LineChart(
        modifier = androidx.compose.ui.Modifier
            .fillMaxWidth()
            .height(300.dp),
        lineChartData = lineChartData
    )

}

