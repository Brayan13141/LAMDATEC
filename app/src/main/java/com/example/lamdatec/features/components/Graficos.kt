package com.example.lamdatec.features.components

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.with
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
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
import com.example.lamdatec.features.Graficos.MQ2.data.FiltrosFecha

@Composable
fun PantallaConGraficoGENERAL(
    navController: NavHostController,
    titulo: String,
    puntosGrafico: List<Point>,
    Valor: Int,
    fechas: List<String>,
    FiltroSeleccionado: (Pair<FiltrosFecha, String?>) -> Unit
) {
    PPantallas(navController) {
        Column(modifier = Modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Botones(fechas) { filtroSeleccionado ->
                FiltroSeleccionado(filtroSeleccionado)
            }

            if (puntosGrafico.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(1.dp, Color.Black, shape = MaterialTheme.shapes.large)
                ) {
                    Grafico(puntosGrafico)
                }

            }
            Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center)
            {
                ValorCard(Valor.toString(), "PPP")
            }
        }
    }
}

//CARD DE PPM
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

    //Efecto que actualiza el color de borde cada vez que cambia el valor
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


//BOTONES DE FILTRADO
@Composable
fun Botones(
    fechas: List<String>,  // Recibe el par de listas de fechas
    FiltroSeleccionado: (Pair<FiltrosFecha, String?>) -> Unit
) {
    //INDICES PARA LOS BOTONES DE FILTRADO
    var BotonFechaI by remember { mutableStateOf(-1) }
    var BotonCategoI by remember { mutableStateOf(-1) }
    //FILTRO SELECCIONADO
    var filtroI: FiltrosFecha by remember { mutableStateOf(FiltrosFecha.NINGUNO) }
    val dateParts = obtenerDiasYMeses(fechas)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()
    ) {
        if (BotonCategoI == 0)
            Texto_Linea(text = "Dia")
        else
            Texto_Linea(text = "No disponible")
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dateParts.first.size) { fechaIndex ->
                val D = dateParts.first[fechaIndex]
                val M = dateParts.second[fechaIndex]
                val Fecha = dateParts.third[fechaIndex]
                var isSelected = (BotonFechaI == fechaIndex) && BotonCategoI == 0
                val animatedBorderColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF4CAF50) else Color.LightGray,
                    animationSpec = tween(durationMillis = 300)
                )
                Card(
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.elevatedCardElevation(4.dp),
                    modifier = Modifier
                        .width(70.dp)
                        .padding(4.dp)
                        .border(
                            width = 2.dp,
                            color = animatedBorderColor,
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clickable {
                            //ASEGURAR QUE LA CATEGORIA DIA ESTA SELECCOINADA
                            if (BotonCategoI == 0) {
                                BotonFechaI = if (isSelected) -1 else fechaIndex
                                isSelected = BotonFechaI == fechaIndex
                                if (isSelected) {
                                    FiltroSeleccionado(Pair(filtroI, Fecha))
                                }
                            }
                            // Log.e("BOTON-FECHA", "SELECCIONADO: $isSelected - FECHA: $BotonFechaI")
                        }
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(if (isSelected) Color(0xFF4CAF50) else Color.White)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = D + "-" + Mes(M.toInt()),
                            color = if (isSelected) Color.White else Color.Black,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
        Texto_Linea(text = "Filtrar por:")
        // Los botones de opciones de filtro
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            val opciones = listOf(
                FiltrosFecha.DIA to "Por día",
                FiltrosFecha.SEMANA to "Por semana",
                FiltrosFecha.MES to "Por mes"
            )
            items(opciones.size) { opcionIndex ->
                val (filtro, label) = opciones[opcionIndex]
                var isSelected = BotonCategoI == opcionIndex
                val buttonBackgroundColor by animateColorAsState(
                    targetValue = if (isSelected) Color(0xFF4CAF50) else Color.LightGray,
                    animationSpec = tween(durationMillis = 300)
                )
                Button(
                    onClick = {
                        //LA PRIMERA VEZ SE LLAMAN LOS DATOS DEL DIA ACTUAL ENVIANDO NULL COMO PARAMETRO
                        BotonCategoI = if (isSelected) -1 else opcionIndex
                        isSelected = BotonCategoI == opcionIndex
                        // Log.e("BOTON-CATEG", "SELECCIONADO: $isSelected - VALOR: $BotonCategoI")
                        if (opcionIndex != 0)
                            BotonFechaI = -1
                        if (isSelected) {
                            filtroI = filtro
                            FiltroSeleccionado(Pair(filtro, null))
                        } else {
                            FiltroSeleccionado(Pair(FiltrosFecha.NINGUNO, null))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = buttonBackgroundColor),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(100.dp)
                        .padding(vertical = 4.dp)
                ) {
                    Text(
                        text = label,
                        color = if (isSelected) Color.White else Color.Black,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}


@Composable
fun Grafico(Puntos: List<Point>) {
    var puntosGrafico by remember { mutableStateOf(Puntos) }

    puntosGrafico = Puntos.takeLast(10) // Mantener los últimos 10 puntos

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


// Función para obtener el nombre del mes en formato de texto
private fun Mes(monthNumber: Int): String {
    val months = listOf(
        "ENE", "FEB", "MAR", "ABR", "MAY", "JUN",
        "JUL", "AGO", "SEP", "OCT", "NOV", "DIC"
    )
    return months.getOrElse(monthNumber - 1) { "Mes Desconocido" }
}

//TEXTO ENTRE 2 LINEAS..
@Composable
fun Texto_Linea(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Gray,
    padding: Dp = 8.dp
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        Divider(
            color = color,
            modifier = Modifier
                .weight(1f)
                .padding(end = padding)
        )

        Text(
            text = text,
            color = color,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Divider(
            color = color,
            modifier = Modifier
                .weight(1f)
                .padding(start = padding)
        )
    }
}


fun obtenerDiasYMeses(fechas: List<String>): Triple<List<String>, List<String>,List<String>> {
    var Dias: List<String> = emptyList()
    var Meses: List<String> = emptyList()
    var fechas1: List<String> = emptyList()
    fechas.forEach { fecha ->
        val dateParts = fecha.split("-")
        if (dateParts.size == 3) {
            val Dia = dateParts[2]
            val Mes = dateParts[1]
            Dias = Dias + Dia
            Meses = Meses + Mes
            fechas1 = fechas1 + fecha
        }
    }
    return Triple(Dias, Meses, fechas1)
}