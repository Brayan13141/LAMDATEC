package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.lamdatec.Interfaz.Nav.PantallasNav
import com.example.lamdatec.R
import kotlinx.coroutines.launch


@Composable
fun DrawerMenu(navController: NavHostController, scaffoldState: ScaffoldState) {
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
        DrawerItem("Pantalla 1", icon = Icons.Default.Home) {
            navController.navigate( PantallasNav.PRINCIPAL.route)
            coroutineScope.launch { scaffoldState.drawerState.close() }
        }
        DrawerItem("Pantalla 3", icon = Icons.Default.Settings) {
            navController.navigate("pantalla3")
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
fun DrawerItem(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
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