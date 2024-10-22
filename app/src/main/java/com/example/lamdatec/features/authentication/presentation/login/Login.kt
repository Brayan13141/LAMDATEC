package com.example.lamdatec.features.authentication.presentation.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.lamdatec.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick: (String, String) -> Unit) {
    // Variables para el estado de los campos de entrada
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = stringResource(R.string.lblLogIn))},
                //backgroundColor = MaterialTheme.colorScheme.secondary
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo de la app
            Image(
                painter = painterResource(id = R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e), // Reemplaza con tu logo
                contentDescription = null,
                modifier = Modifier.size(128.dp)
                    .size(100.dp)
                    .clip(CircleShape)
            )

            Spacer(modifier = Modifier.height(64.dp))

            // Campo de entrada para correo electrónico
            OutlinedTextField(
                value = email.value,
                onValueChange = { email.value = it },
                label = { Text(text = stringResource(R.string.lblEmail)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Email"
                    )
                },
                /*colors = TextFieldDefaults.(
                        textColor = MaterialTheme.colorScheme.onPrimary, // Color del texto
                    cursorColor = MaterialTheme.colorScheme.primary, // Color del cursor
                    focusedBorderColor = MaterialTheme.colorScheme.primary, // Borde cuando está enfocado
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground // Borde cuando no está enfocado
                )*/
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de entrada para la contraseña
            OutlinedTextField(
                value = password.value,
                onValueChange = { password.value = it },
                label = { Text(text = stringResource(R.string.lblPassword)) },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null
                    )
                },
                /*modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.outlinedTextFieldColors(
                    textColor = MaterialTheme.colorScheme.onPrimary, // Color del texto
                    cursorColor = MaterialTheme.colorScheme.primary, // Color del cursor
                    focusedBorderColor = MaterialTheme.colorScheme.primary, // Borde cuando está enfocado
                    unfocusedBorderColor = MaterialTheme.colorScheme.onBackground // Borde cuando no está enfocado
                )*/
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para iniciar sesión
            Button(
                onClick = { onLoginClick(email.value, password.value) },
                /*colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )*/
                shape = shapes.small,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.btnLogIn),
                    //color = Color.White,
                    //fontSize = 18.sp
                )
            }
        }
    }
}