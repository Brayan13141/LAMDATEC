package com.example.lamdatec.Interfaz.Pantallas

import android.util.Log
import androidx.compose.animation.core.EaseInOutCubic
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.internal.composableLambdaInstance
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathSegment
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.google.android.gms.vision.text.Line
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import kotlinx.coroutines.flow.Flow
import kotlin.random.Random


@Composable
fun PantallaConGrafico(navController: NavHostController) {
    val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")
    var control by remember { mutableStateOf(1) }
    var puntosGrafico by remember { mutableStateOf(listOf<Point>()) }
    PPantallas(navController, stringResource(R.string.lblSensor1)) {

        ConsultarDatosSensores() { newPoints->
            puntosGrafico = newPoints

        }
            Log.e("Firebase", "Valor actualizado en Firebase: $puntosGrafico")
        if(!puntosGrafico.isEmpty())
        {
            Grafico(puntosGrafico)
        }

    }

}


fun obtenerValorMQ2(){
    // Referencia a la base de datos

}



// Función para consultar los datos del sensor en Firebase
@Composable
fun ConsultarDatosSensores(actualizar: (List<Point>) -> Unit) {
    var airValues by remember { mutableStateOf(listOf<Float>()) }
    val database = FirebaseDatabase.getInstance().reference

    database.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").get().addOnSuccessListener { snapshot ->
        database.child("LAMDATEC/Sensores/SENSORES/MQ2/Valor").addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val valor = snapshot.getValue(Int::class.java) ?: 0
                airValues = airValues + valor.toFloat()
                actualizar(airValues.mapIndexed { index, value ->
                    Point(index.toFloat(), value)
                })
                Log.e("Firebase", "Valor actualizado en Firebase: $valor")
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }.addOnFailureListener { exception ->
        Log.e("Firebase", "Error al leer el valor actual de 'air': ${exception.message}")
    }

}


@Composable
fun Grafico(Puntos : List<Point>)
{

    val xAxisData = AxisData.Builder()
        .axisStepSize(100.dp)
        .backgroundColor(color = Color.White)
        .steps(Puntos.size - 1)
        .labelData { i -> i.toString() }
        .labelAndAxisLinePadding(15.dp)
        .build()
    val steps = 10
    val yAxisData = AxisData.Builder()
        .steps(10)
        .backgroundColor(Color.White)
        .labelAndAxisLinePadding(20.dp)
        .labelData { i ->
            val yScale = 100 / steps
            (i * yScale).toString()
        }.build()

    val lineChartData = LineChartData(
        linePlotData = LinePlotData(
            lines = listOf(
                co.yml.charts.ui.linechart.model.Line(
                    dataPoints = Puntos,
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

