package com.example.lamdatec.features.components.MenuLat

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.lamdatec.R
import com.example.lamdatec.core.navigation.Destinations

@Composable
fun MenuDrawer(
    navController: NavHostController
) {
    val currentEntry = navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry.value?.destination?.route

    ModalDrawerSheet(
        drawerShape = RectangleShape,
        modifier = Modifier.fillMaxWidth(0.8f)
    ) {
        Text(
            text = stringResource(id = R.string.lblWelcome),
            style = typography.titleLarge,
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.Start)
        )
        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    // mandar a perfil
                }
        ) {
            Text(
                text = stringResource(R.string.lblAccount),
                style = typography.labelSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.graphicsLayer(alpha = 0.5f).padding(bottom = 8.dp, start = 8.dp)
            )

            Text(
                text = "Brayan Sanchez Monroy",
                style = typography.labelLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Text(
                text = "s20120185@alumnos.itsur.edu.mx",
                style = typography.labelLarge,
                fontWeight = FontWeight.Thin,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(Modifier.size(8.dp))
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        MenuItem(
            label = stringResource(id = R.string.lblHome),
            icon = Icons.Outlined.Home,
            iconDescription = "",
            selected = currentRoute == Destinations.Principal.route,
            onClick = {
                navController.navigate(Destinations.Principal.route) {
                    // evitar mas pantallas en la pila
                    launchSingleTop = true
                    // borrar toda la pila
                    popUpTo(0) { inclusive = true }
                }
            }
        )

        MenuItem(
            label = stringResource(id = R.string.lblSensorMQ2),
            icon = Icons.Outlined.Home,
            iconDescription = "",
            selected = currentRoute == Destinations.SensorMQ2.route,
            onClick = {
                navController.navigate(Destinations.SensorMQ2.route) {
                    launchSingleTop = true
                }
            }
        )

        MenuItem(
            label = stringResource(id = R.string.lblSensorMQ7),
            icon = Icons.Outlined.Home,
            iconDescription = "",
            selected = currentRoute == Destinations.SensorMQ7.route,
            onClick = {
                navController.navigate(Destinations.SensorMQ7.route) {
                    launchSingleTop = true
                }
            }
        )

        Spacer(Modifier.weight(1f))

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp)
        )

        MenuItem(
            label = stringResource(id = R.string.lblLogOut),
            icon = Icons.AutoMirrored.Outlined.Logout,
            iconDescription = "",
            selected = true,
            logout = true,
            onClick = {
                navController.navigate(Destinations.Login.route) {
                    launchSingleTop = true
                    popUpTo(0) { inclusive = true }
                }
            }
        )
    }
}