package com.example.lamdatec.features.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lamdatec.core.navigation.PantallasNav
import com.example.lamdatec.R
import kotlinx.coroutines.launch


@Composable
fun Menu(navController: NavHostController, scaffoldState: ScaffoldState) {
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Sección de la foto y el nombre del usuario
        UserSection()

        Divider(modifier = Modifier.padding(vertical = 8.dp))

        // Opciones de navegación
        DrawerItem(modifier = Modifier
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(
                androidx.compose.material3.MaterialTheme.colorScheme.inversePrimary.copy(
                    0.5f
                )
            )
            ,"INICIO", icon = Icons.Default.Home) {
            navController.navigate( PantallasNav.PRINCIPAL.route)
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }

        DrawerItem(modifier = Modifier,"SENSOR MQ2", icon = Icons.Default.Settings) {
            navController.navigate(PantallasNav.SENSOR_MQ2.route)
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }
        DrawerItem(modifier = Modifier,"SENSOR MQ7", icon = Icons.Default.Settings) {
            navController.navigate(PantallasNav.SENSOR_MQ7.route)
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }
        Spacer(modifier = Modifier
            .height(330.dp)
            .background(Color.Transparent))
        Divider(modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth())
        DrawerItem(modifier = Modifier
            .align(Alignment.End)
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .background(androidx.compose.material3.MaterialTheme.colorScheme.error.copy(0.5f))
            ,"SALIR", icon = Icons.Default.ArrowBack) {
            navController.navigate(PantallasNav.LOGIN.route)
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }
    }
}

@Composable
fun UserSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Foto del usuario
        Image(
            painter = painterResource(id = R.drawable.usuario), // Imagen de usuario (coloca tu recurso de imagen)
            contentDescription = "Foto de usuario",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Nombre del usuario
        Text(text = "Nombre del Usuario", style = MaterialTheme.typography.h6)

        Spacer(modifier = Modifier.height(4.dp))

        // Email del usuario o información adicional
        Text(text = "usuario@example.com", style = MaterialTheme.typography.body2, color = Color.Gray)
    }
}

@Composable
fun DrawerItem(modifier: Modifier,text: String, icon: ImageVector, onClick: () -> Unit) {
    Spacer(modifier = Modifier.height(1.dp))
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(topEnd = 16.dp, bottomEnd = 16.dp))
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = text)
        Spacer(modifier = Modifier.width(16.dp))
        Text(text = text, style = MaterialTheme.typography.subtitle1)
    }
}