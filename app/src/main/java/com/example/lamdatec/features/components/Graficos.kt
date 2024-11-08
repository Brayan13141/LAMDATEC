package com.example.lamdatec.features.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
    puntosGrafico: List<Point>,
    valor : Int
) {

    PPantallas(navController) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Botones()

            if (puntosGrafico.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black, shape = MaterialTheme.shapes.large)
                ) {
                    Grafico(puntosGrafico)
                }
                Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center)
                {
                    ValorCard( valor.toString(), "PPP")
                }
            }

        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ValorCard(valor: String, descripcion: String) {
    // Estado para rastrear el color del borde
    var bordeColor by remember { mutableStateOf(Color.Transparent) }
    // Cambiar a verde y luego volver a transparente
    val animatedBorderColor by animateColorAsState(
        targetValue = bordeColor,
        animationSpec = tween(durationMillis = 500) // Duración de la animación
    )

    // Efecto que actualiza el color de borde cada vez que cambia el valor
    LaunchedEffect(valor) {
        bordeColor = Color(0xFF4CAF50) // Verde en el cambio
        kotlinx.coroutines.delay(500) // Duración visible del verde
        bordeColor = Color.Transparent // Vuelve a ser transparente
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
        modifier = Modifier
            .size(width = 100.dp, height = 80.dp)
            .padding(8.dp)
            .border(width = 2.dp, color = animatedBorderColor, shape = RoundedCornerShape(16.dp))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Animación de cambio de valor
            AnimatedContent(
                targetState = valor,
                transitionSpec = {
                    fadeIn() with fadeOut()
                }
            ) { targetValor ->
                Text(
                    text = targetValor,
                    color = Color(0xFF4CAF50),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = descripcion,
                color = Color.Gray,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun Botones() {
    Column(modifier = Modifier.padding(16.dp)
    ) {
        // Filas de Fechas
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val fechas = listOf(
                "01 OCT", "02 OCT", "03 OCT", "04 OCT", "05 OCT", "06 OCT",
                "07 OCT", "08 OCT", "09 OCT", "10 OCT"
            ) // Agrega más fechas si necesitas
            items(fechas.size - 1) { fecha ->
                Card(
                    shape = MaterialTheme.shapes.small,
                    modifier = Modifier
                        .padding(4.dp)
                        .width(60.dp)
                ) {
                    Text(
                        text = fechas.get(fecha).toString(),
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
                        containerColor = if (opcion == "Por día") androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.LightGray
                    ),
                    modifier = Modifier.padding(4.dp)
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
fun Grafico(Puntos: List<Point>) {
    var puntosGrafico by remember { mutableStateOf(Puntos) }

    // Actualizamos los puntos solo cuando los datos cambian
    LaunchedEffect(Puntos) {
        puntosGrafico = Puntos.takeLast(9) // Mantener los últimos 10 puntos
    }
    val steps = 15

    if (puntosGrafico.isNotEmpty()) {
        // Valor máximo y mínimo del eje Y basado en los valores actuales de los puntos
        val yAxisMaxValue by remember {
            derivedStateOf { puntosGrafico.maxOfOrNull { it.y.toInt() } ?: 0 }
        }
        val yAxisMinValue by remember {
            derivedStateOf { puntosGrafico.minOfOrNull { it.y.toInt() } ?: 0 }
        }


        // Configuración del eje X
        val xAxisData = AxisData.Builder()
            .axisStepSize(40.dp)
            .backgroundColor(color = Color.White)
            .steps(steps)
            .labelData { index ->
                if (index < puntosGrafico.size) {
                    puntosGrafico[index].x.toInt().toString() // Usa el valor `x` en cada punto
                } else {
                    ""
                }
            }
            .labelAndAxisLinePadding(10.dp)
            .build()

        // Configuración del eje Y para mostrar valores exactos
        val yAxisData = AxisData.Builder()
            .steps(steps)
            .backgroundColor(Color.White)
            .labelAndAxisLinePadding(20.dp)
            .labelData { i ->
                // Dividir el rango exacto en `steps` partes y mostrar los valores correspondientes
                val range = yAxisMaxValue - yAxisMinValue
                val exactValue = yAxisMinValue + (i * range / steps)
                exactValue.toString()
            }.build()

        // Configuración del gráfico de líneas
        val lineChartData = LineChartData(
            linePlotData = LinePlotData(
                lines = listOf(
                    co.yml.charts.ui.linechart.model.Line(
                        dataPoints = puntosGrafico.map {
                            Point(
                                it.x.toFloat(),
                                it.y.toFloat()
                            )
                        },
                        LineStyle(
                            color = MaterialTheme.colorScheme.primary,
                            lineType = LineType.SmoothCurve(isDotted = false)
                        ),
                        IntersectionPoint(),
                        SelectionHighlightPoint(),
                        ShadowUnderLine(
                            alpha = 0.5f,
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
            paddingRight = 3.dp,
            xAxisData = xAxisData,
            yAxisData = yAxisData,
            gridLines = GridLines(),
            backgroundColor = Color.White
        )

        // Composable del gráfico
        LineChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            lineChartData = lineChartData
        )
    }
}
