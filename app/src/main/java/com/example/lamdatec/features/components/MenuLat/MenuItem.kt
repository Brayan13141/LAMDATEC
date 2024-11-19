package com.example.lamdatec.features.components.MenuLat

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun MenuItem(
    modifier: Modifier = Modifier,
    label: String = "Ejemplo",
    icon: ImageVector = Icons.Outlined.Home,
    iconDescription: String = "Ejemplo",
    selected: Boolean = false,
    logout: Boolean = false,
    onClick: () -> Unit = {}
) {

    val colors = if(!logout) {
        NavigationDrawerItemDefaults.colors()
    } else {
        NavigationDrawerItemDefaults.colors(
            selectedContainerColor = colorScheme.errorContainer,
            selectedTextColor = colorScheme.onErrorContainer,
            selectedIconColor = colorScheme.onErrorContainer,
        )
    }

    NavigationDrawerItem(
        label = {
            Text(
                text = label,
                fontWeight = FontWeight.Bold
            )
        },
        icon = {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription
            )
        },
        selected = selected,
        shape = RoundedCornerShape(topEnd = 100.dp, bottomEnd = 100.dp),
        onClick = onClick,
        colors = colors,
        modifier = modifier
            .padding(bottom = 8.dp)
            .fillMaxWidth(0.9f)
            .height(50.dp)
    )
}