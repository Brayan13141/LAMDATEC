package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun PantallaInicio(navController: NavHostController) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = { Text("BIENVENIDO A LAMDATEC") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()  // Abre el menú desplegable
                        }
                    }) {
                        Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                    }
                }
            )
        },
        drawerContent = {
            DrawerMenu(navController = navController, scaffoldState = scaffoldState)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                Text("Bienvenido a la pantalla principal", modifier = Modifier.padding(16.dp))
            }
        }
    )
}