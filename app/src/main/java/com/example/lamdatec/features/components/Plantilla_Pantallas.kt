package com.example.lamdatec.features.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.example.lamdatec.R
import com.example.lamdatec.app.theme.green
import com.example.lamdatec.app.theme.onGreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PPantallas(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val openDrawer = {
        coroutineScope.launch {
            drawerState.open()
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(
                navController = navController
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {

                    },
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                openDrawer()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Menu,
                                contentDescription = stringResource(R.string.lblOpenMenu)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = green,
                        titleContentColor = onGreen,
                        navigationIconContentColor = onGreen,
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxHeight()
                    .fillMaxWidth()
            ) {
                content()
            }
        }
    }
}
