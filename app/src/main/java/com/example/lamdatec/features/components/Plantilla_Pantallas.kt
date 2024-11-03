package com.example.lamdatec.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import com.example.lamdatec.R

@Composable
fun PPantallas(navController: NavHostController, Titulo: String, content: @Composable () -> Unit) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.inversePrimary,
                modifier = Modifier.height(100.dp),
                title = {
                    Text(
                        text = Titulo,
                        fontSize = 24.sp,
                        color = Color.White
                    )
                    Row(
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                            .padding(top = 30.dp, bottom = 10.dp)
                    ) {
                        // Icono de Bluetooth
                        IconButton(
                            onClick = { /* Acción para Bluetooth */ },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.icons8_hidr_geno_50), // Reemplaza con tu recurso
                                contentDescription = "Bluetooth",
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Icono de Wi-Fi
                        IconButton(
                            onClick = { /* Acción para Wi-Fi */ },
                            modifier = Modifier
                                .size(40.dp)
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(4.dp)
                        ) {
                            Icon(modifier = Modifier.size(80.dp),
                                painter = painterResource(id = R.drawable.ico_wifi_off), // Reemplaza con tu recurso
                                contentDescription = "Wi-Fi",
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Estado de conexión
                        Box(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(horizontal = 12.dp, vertical = 8.dp)
                        ) {
                            Text(
                                text = "Conectado",
                                fontSize = 16.sp,
                                color = Color.Black
                            )
                        }

}
                    },
                navigationIcon= {
                    Box(modifier = Modifier.fillMaxHeight()
                        , Alignment.BottomCenter)
                    {

                        Row(modifier = Modifier.height(50.dp).width(50.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color.White),
                        ) {
                            IconButton(onClick = {
                                coroutineScope.launch {
                                    scaffoldState.drawerState.open() // Abre el menú desplegable
                                }
                            })
                            {
                                Icon(
                                    Icons.Default.Menu,
                                    contentDescription = "Abrir menú",
                                    modifier = Modifier.size(40.dp)
                                )
                            }
                        }
                    }

                }
            )
        },
        drawerContent = {
            Menu(navController = navController, scaffoldState = scaffoldState)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    )
}
