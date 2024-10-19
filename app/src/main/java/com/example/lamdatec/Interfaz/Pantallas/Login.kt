package com.example.lamdatec.Interfaz.Pantallas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier 
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.lamdatec.R 


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginClick: (String, String) -> Unit) {
    // Variables para el estado de los campos de entrada
    var email = remember { mutableStateOf("") }
    var password = remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(modifier = Modifier.height(70.dp),
                   title = { Text("Iniciar Sesión")},
                    backgroundColor = MaterialTheme.colorScheme.secondary
                )
                 },
        content = { padding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
                contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    // Logo de la app
                    Image(
                        painter = painterResource(id = R.drawable._721ba60e_8eec_4bdc_a184_6c9d2b89090e), // Reemplaza con tu logo
                        contentDescription = null,
                        modifier = Modifier.size(128.dp)
                            .size(100.dp)
                            .clip(CircleShape)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Campo de entrada para correo electrónico
                    OutlinedTextField(
                        value = email.value,
                        onValueChange = { email.value = it },
                        label = { Text("Correo Electrónico") },
                        leadingIcon = {
                            Icon(Icons.Filled.Person, contentDescription = "Email")
                        },
                        modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onPrimary, // Color del texto
                            cursorColor = MaterialTheme.colorScheme.primary, // Color del cursor
                            focusedBorderColor = MaterialTheme.colorScheme.primary, // Borde cuando está enfocado
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground // Borde cuando no está enfocado
                        )
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Campo de entrada para la contraseña
                    OutlinedTextField(
                        value = password.value,
                        onValueChange = { password.value = it },
                        label = { Text("Contraseña") },
                        leadingIcon = {
                            Icon(Icons.Filled.Lock, contentDescription = "Password")
                        },
                        visualTransformation = PasswordVisualTransformation(),
                        modifier = Modifier.fillMaxWidth(), colors = TextFieldDefaults.outlinedTextFieldColors(
                            textColor = MaterialTheme.colorScheme.onPrimary, // Color del texto
                            cursorColor = MaterialTheme.colorScheme.primary, // Color del cursor
                            focusedBorderColor = MaterialTheme.colorScheme.primary, // Borde cuando está enfocado
                            unfocusedBorderColor = MaterialTheme.colorScheme.onBackground // Borde cuando no está enfocado
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    // Botón para iniciar sesión
                    Button(
                        onClick = { onLoginClick(email.value, password.value) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text("Iniciar Sesión", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }
    )
}


