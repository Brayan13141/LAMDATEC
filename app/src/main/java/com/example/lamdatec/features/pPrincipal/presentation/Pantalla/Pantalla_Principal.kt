package com.example.lamdatec.features.pPrincipal.presentation.Pantalla

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.lamdatec.R
import com.example.lamdatec.features.components.PPantallas
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.material3.Button
import androidx.compose.ui.text.font.FontWeight

@Composable
fun PantallaInicio(navController: NavHostController, viewModel: viewM_Principal = viewModel()) {
    PPantallas(navController, "BIENVENIDO A LAMDATEC") {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
        ) {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)) {
                Text("PARTES POR MILLON", style = MaterialTheme.typography.titleMedium)
            }
            SensoresPPM(viewModel)
            MotoAnimationScreen(viewModel)
        }
    }
}

@Composable
fun SensoresPPM(viewModel: viewM_Principal) {
    // Tarjeta principal con bordes redondeados y fondo
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(18.dp))
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Fecha y hora
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = viewModel.currentDate,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 16.sp
                )
                Text(
                    text = viewModel.currentTime,
                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                    fontSize = 16.sp
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Dos columnas para mostrar los seis sensores
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Primera columna de sensores
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SensorCard(
                        title = "Aire",
                        valor = viewModel.ppmAir.collectAsState().value.toString(),
                        icon = painterResource(id = R.drawable.icons8_calidad_del_aire_50)
                    )
                    SensorCard(
                        title = "CO2",
                        valor = viewModel.ppmCO2.collectAsState().value.toString(),
                        icon = painterResource(id = R.drawable.icons8_co2_50)
                    )
                    SensorCard(
                        title = "Humedad",
                        valor = viewModel.ppmHumedad.collectAsState().value.toString(),
                        icon = painterResource(id = R.drawable.icons8_hidr_geno_50)
                    )
                }

                // Segunda columna de sensores
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SensorCard(
                        title = "Temperatura",
                        valor = viewModel.ppmAir.collectAsState().value.toString(), // Placeholder para otro sensor
                        icon = painterResource(id = R.drawable.ico_wifi_off)
                    )
                    SensorCard(
                        title = "Presión",
                        valor = viewModel.ppmCO2.collectAsState().value.toString(), // Placeholder para otro sensor
                        icon = painterResource(id = R.drawable.icons8_hidr_geno_50)
                    )
                    SensorCard(
                        title = "Gas",
                        valor = viewModel.ppmHumedad.collectAsState().value.toString(), // Placeholder para otro sensor
                        icon = painterResource(id = R.drawable.icons8_calidad_del_aire_50)
                    )
                }
            }
        }
    }
}


@Composable
fun MotoAnimationScreen(viewModel: viewM_Principal) {
    val isMotoOn by remember { viewModel.isMotoOn }
    var controlMotoOn by remember {viewModel.ControlMotoOn }
    val isButtonEnabled by viewModel.isButtonEnabled.collectAsState()

    // Animación para el movimiento horizontal
    val Sal = updateTransition(targetState = isMotoOn, label = "Moto")
    val Ent = updateTransition(targetState = controlMotoOn, label = "Moto")

    val Salir by Sal.animateDp(
        transitionSpec = {
            tween(durationMillis = 850, easing = FastOutSlowInEasing)
        },
        label = "Offset Animation"
    ) { state ->
        if (state) 250.dp else 0.dp // SE MUEVE A LA DERECHA DEL VALOR(ENCENDER-SALIDA)
    }
    val Entrar by Ent.animateDp(
        transitionSpec = {
            tween(durationMillis = 850, easing = FastOutSlowInEasing)
        },
        label = "Offset Animation"
    ) { state ->
        if (state) 0.dp else -250.dp // SE MUEVE A LA DERECHA DEL VALOR(ENCENDER-ENTRADA)
    }
    val Salir2 by Sal.animateDp(
        transitionSpec = {
            tween(durationMillis = 850, easing = FastOutSlowInEasing)
        },
        label = "Offset Animation"
    ) { state ->
        if (state) 0.dp else 250.dp // SE MUEVE A LA DERECHA DEL VALOR(APAGAR-SALIDA)
    }
    val Entrar2 by Ent.animateDp(
        transitionSpec = {
            tween(durationMillis = 850, easing = FastOutSlowInEasing)
        },
        label = "Offset Animation"
    ) { state ->
        if (state) -250.dp else 0.dp // SE MUEVE A LA DERECHA DEL VALOR(ENCENDER-ENTRADA)
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(250.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        // Mostrar la imagen de la moto dependiendo del estado
        if (isMotoOn) {//IMAGEN VACIA SALIENDO
            Image(
                painter = painterResource(
                    id = R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e_removebg_preview
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = Salir)
                    .clip(CircleShape)
            )
            if (Salir.value == 250.dp.value)//IMAGEN VACIA ENTRANDO
            {
                controlMotoOn = true//ENTRADA DE LA MOTO
                Image(
                    painter = painterResource(
                        id =   R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e_removebg_preview
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = Entrar)
                        .clip(CircleShape)
                )
                if(Entrar.value == 0.dp.value){//MOSTRAR IMAGEN CON FONDO VERDE(CONECTADO)
                    Image(
                        painter = painterResource(
                            id =   R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(100.dp)
                            .offset(x = Entrar)
                            .clip(CircleShape)
                    )
                }
            }
        }else{
            Image(
                painter = painterResource(
                    id = R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e_removebg_preview
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(100.dp)
                    .offset(x = Salir2)
                    .clip(CircleShape)
            )
            if (Salir2.value == 250.dp.value)//IMAGEN VACIA ENTRANDO
            {
                controlMotoOn =false
                Image(
                    painter = painterResource(
                        id =   R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e_removebg_preview
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = Entrar2)
                        .clip(CircleShape)
                )
            }
            if (Entrar2.value == 0.dp.value)//MOSTRAR IMAGEN CON FONDO VERDE(CONECTADO)
            {
                Image(
                    painter = painterResource(
                        id =   R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e_removebg_preview
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp)
                        .offset(x = Entrar2)
                        .clip(CircleShape)
                        .background(Color.Red)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button( colors = ButtonDefaults.buttonColors(
            containerColor = if (viewModel.isMotoOn.value) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surfaceVariant,
            contentColor = if (viewModel.isMotoOn.value)  Color.Red else Color.Green  // Cambia el color del fondo
        ),
            onClick = {
                 viewModel.CambiarMotoStatus()
            }, enabled = isButtonEnabled
            ,
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = shapes.small

        ) {
            Text(text = if (isMotoOn) "APAGAR" else "ENCENDER")
        }
    }
}
@Composable
fun SensorCard(
    title: String,
    icon: Painter,
    valor: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp) // Reducimos el padding vertical para un diseño más compacto
            .clip(shape = MaterialTheme.shapes.small),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Icono
        Icon(
            painter = icon,
            contentDescription = title,
            modifier = Modifier
                .padding(8.dp) // Reducimos el padding alrededor del icono
                .size(24.dp), // Reducimos el tamaño del icono
            tint = Color.Black
        )

        // Texto del título
        Text(
            text = title,
            fontSize = 14.sp, // Reducimos el tamaño de la fuente del título
            color = Color.Black,
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp) // Menor padding lateral
        )

        // Valor del sensor
        Text(
            text = valor,
            fontSize = 14.sp, // Reducimos el tamaño de la fuente del valor
            color = Color.Green,
            modifier = Modifier.padding(end = 8.dp) // Menor padding al final
        )
    }

    // Divider más delgado para una separación visual ligera
    Divider(
        modifier = Modifier
            .fillMaxWidth()
            .height(0.5.dp), // Hacemos el Divider más delgado
        color = Color.Gray.copy(alpha = 0.5f)
    )
}
