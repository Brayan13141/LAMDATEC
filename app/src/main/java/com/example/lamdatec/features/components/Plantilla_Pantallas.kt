package com.example.lamdatec.features.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch


@Composable
fun PPantallas(navController: NavHostController,Titulo: String, content: @Composable () -> Unit ) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                backgroundColor = MaterialTheme.colorScheme.primary,
                modifier = Modifier.height(70.dp),
                title = {
                        Text(text = Titulo,fontSize = 21.sp)
                },
                navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                scaffoldState.drawerState.open() // Abre el menú desplegable
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú",
                                modifier = Modifier.size(30.dp))
                        }
                }
            )
        },
        drawerContent = {
            Menu(navController = navController, scaffoldState = scaffoldState)
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).fillMaxHeight()
                .fillMaxWidth()) {
                  content()
            }
        }
    )
}