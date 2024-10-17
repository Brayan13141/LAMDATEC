package com.example.lamdatec.Interfaz.Pantallas

import android.graphics.Color
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp

@Composable
fun PantallaConGrafico() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título
        Text(
            text = "Datos del Sensor",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Gráfico
        LineChartView(
            modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
        )
    }
}

// Composable para el gráfico
@Composable
fun LineChartView(modifier: Modifier = Modifier) {

}