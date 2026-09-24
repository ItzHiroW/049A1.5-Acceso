package com.example.a049a15acceso

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.light(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.BLACK
            )
        )
        setContent {
            MaterialTheme {
                AppNavegacion()
            }
        }
    }
}

// ----------------------------------------------------
// 1. SISTEMA DE NAVEGACIÓN
// ----------------------------------------------------
@Composable
fun AppNavegacion() {
    val navController = rememberNavController()

    // NavHost gestiona qué pantalla se muestra según la "ruta" actual
    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            PantallaLogin(navController = navController)
        }
        composable("perfil") {
            PantallaPerfil(navController = navController)
        }
    }
}

// ----------------------------------------------------
// 2. PANTALLA DE LOGIN
// ----------------------------------------------------
@Composable
fun PantallaLogin(navController: NavController) {
    // Variables de estado para los campos de texto
    var noControl by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var mostrarError by remember { mutableStateOf(false) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(28.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo del Tec
            Image(
                painter = painterResource(id = R.drawable.logo_tec),
                contentDescription = "Logo del Tec",
                modifier = Modifier
                    .size(150.dp)
                    .padding(bottom = 16.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = "Acceso a Estudiantes",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Campo: Número de Control
            OutlinedTextField(
                value = noControl,
                onValueChange = {
                    noControl = it
                    mostrarError = false
                },
                label = { Text("Número de Control") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Person, contentDescription = null)
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo: Password (con ocultamiento de caracteres)
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    mostrarError = false
                },
                label = { Text("Contraseña") },
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Lock, contentDescription = null)
                },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            // Mensaje en caso de datos incorrectos
            if (mostrarError) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Número de control o contraseña incorrectos",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(28.dp))

            // Botón de acceso con validación
            Button(
                onClick = {
                    // Validación de credenciales de ejemplo:
                    if (noControl == "23270049" && password == "Zero") {
                        // Navega a la pantalla de perfil y quita login del historial
                        navController.navigate("perfil") {
                            popUpTo("login") { inclusive = true }
                        }
                    } else {
                        mostrarError = true
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Text(text = "Iniciar Sesión", fontSize = 16.sp)
            }
        }
    }
}

// ----------------------------------------------------
// 3. PANTALLA DE PERFIL (DATOS EN FORMATO VERTICAL)
// ----------------------------------------------------
@Composable
fun PantallaPerfil(navController: NavController) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(32.dp))

            // Foto de perfil circular
            Image(
                painter = painterResource(id = R.drawable.foto_perfil),
                contentDescription = "Foto del Alumno",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Datos del Estudiante",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Tarjeta con los datos organizados verticalmente
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    ItemDato(etiqueta = "Nombre Completo", valor = "Angel Ruben Morales Ochoa")
                    ItemDato(etiqueta = "No. de Control", valor = "23270049")
                    ItemDato(etiqueta = "Carrera", valor = "Ing. en Sistemas Computacionales")
                    ItemDato(etiqueta = "Semestre", valor = "8vo Semestre")
                    ItemDato(etiqueta = "Correo Institucional", valor = "l23270049@tuxtla.tecnm.mx")
                    ItemDato(etiqueta = "Estatus", valor = "Activo")
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Botón para cerrar sesión
            OutlinedButton(
                onClick = {
                    navController.navigate("login") {
                        popUpTo("perfil") { inclusive = true }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar Sesión")
            }
        }
    }
}

// Componente auxiliar reutilizable para cada línea de dato
@Composable
fun ItemDato(etiqueta: String, valor: String) {
    Column {
        Text(
            text = etiqueta,
            fontSize = 12.sp,
            color = Color.Gray,
            fontWeight = FontWeight.Medium
        )
        Text(
            text = valor,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}