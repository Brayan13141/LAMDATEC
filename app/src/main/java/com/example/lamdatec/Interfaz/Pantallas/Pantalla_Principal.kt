package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme.shapes
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.lamdatec.Interfaz.Pantallas.Plantilla.PPantallas
import com.example.lamdatec.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.delay


@Composable
fun PantallaInicio(navController: NavHostController) {

    PPantallas(navController,"BIENVENIDO A LAMDATEC")
    {
        Column(
            modifier = Modifier.fillMaxHeight()
                .fillMaxWidth())
        {
            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()
                .padding(5.dp)) {
                Text("PARTES POR MILLON", style = MaterialTheme.typography.titleMedium)
            }
            SensoresPPM()
            MotoAnimationScreen()


        }
    }
}

@Composable
fun SensoresPPM() {
    val database = FirebaseDatabase.getInstance().getReference("LAMDATEC")
    var ppmAir by remember { mutableFloatStateOf(0f) }
    var ppmCO2 by remember { mutableFloatStateOf(0f) }
    var ppmHumedad by remember { mutableFloatStateOf(0f) }

    LaunchedEffect(Unit) {
        database.child("/Sensores/air").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ppmAir = snapshot.getValue(Float::class.java) ?: 0f // Actualiza ppmAir
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        database.child("/Sensores/co").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ppmCO2 = snapshot.getValue(Float::class.java) ?: 0f // Actualiza ppmCO2
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
        database.child("/Sensores/h").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ppmHumedad = snapshot.getValue(Float::class.java) ?: 0f // Actualiza ppmHumedad
            }
            override fun onCancelled(error: DatabaseError) {

            }
        })
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Tarjeta para PPM AIR
            SensorCard(
                valor = ppmAir.toString(),
                modifier = Modifier.border(2.dp, Color(0xFF00BFFF)),
                title = "AIR",
                icon = painterResource(id = R.drawable.icons8_calidad_del_aire_50)
            )
            // Tarjeta para PPM CO2
            SensorCard(
                valor = ppmCO2.toString(),
                modifier = Modifier .border(2.dp, Color(0xFF32CD32)),
                title = "CO2",
                icon = painterResource(id = R.drawable.icons8_co2_50)
            )
            // Tarjeta para PPM HUMEDAD
            SensorCard(
                valor = ppmHumedad.toString(),
                modifier = Modifier .border(2.dp, Color(0xFF4682B4)),
                title = "HUMEDAD",
                icon = painterResource(id = R.drawable.icons8_hidr_geno_50)
            )
        }
    }
}

@Composable
fun SensorCard(title: String, icon: Painter,valor:String,modifier: Modifier) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = 4.dp,
        shape = RoundedCornerShape(5.dp)
    ) {
        Row(
            modifier = modifier.padding(16.dp)
                .clip(shape = RoundedCornerShape(10.dp)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Icon(
                painter = icon,
                contentDescription = title,
                modifier = Modifier.size(32.dp),
                tint = Color.Gray
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = title+":",
                fontSize = 20.sp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(text = valor)
        }
    }
}


@Composable
fun MotoAnimationScreen() {
    // Estado para controlar si la moto está encendida o apagada
    var isMotoOn by remember { mutableStateOf(false) }
    var controlMotoOn by remember { mutableStateOf(false) }

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
        Button(
            onClick = {
                isMotoOn = !isMotoOn // Cambiar el estado al presionar el botón
            },
            modifier = Modifier.align(Alignment.BottomCenter),
            shape = shapes.small

        ) {
            Text(text = if (isMotoOn) "APAGAR" else "ENCENDER")
        }
    }
}
