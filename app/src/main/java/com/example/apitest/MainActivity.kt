package com.example.apitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.apitest.model.Dinosaur
import com.example.apitest.navigation.Routes
import com.example.apitest.ui.theme.GreenJC
import com.example.apitest.view.DinoDetails
import com.example.apitest.view.DinoList

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MyBottomAppBar()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar() {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    var selectedRoute by remember { mutableStateOf(Routes.DinoList.route) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("DinoAPI Explorer") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = GreenJC,
                    titleContentColor = Color.White
                )
            )
        },
        bottomBar = {
            BottomAppBar(
                containerColor = GreenJC
            ) {
                // Botón Home
                IconButton(
                    onClick = {
                        selectedRoute = Routes.DinoList.route
                        navigationController.navigate(Routes.DinoList.route) {
                            popUpTo(0) { inclusive = true }
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio",
                        tint = if (selectedRoute == Routes.DinoList.route) Color.Cyan else Color.White
                    )
                }

                // FAB Central
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            Toast.makeText(context, "Próximamente...", Toast.LENGTH_SHORT).show()
                        },
                        containerColor = Color.White
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Añadir",
                            tint = GreenJC
                        )
                    }
                }

                // Espacio para balance (opcionalmente otro botón)
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Routes.DinoList.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.DinoList.route) { DinoList() }
            composable(Routes.DinoDetails.route) { DinoDetails() }
        }
    }
}

@Composable
fun DinosaurCard(dinosaur: Dinosaur) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Imagen del dinosaurio
            dinosaur.image?.let { url ->
                AsyncImage(
                    model = url,
                    contentDescription = "Imagen de ${dinosaur.name}",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.width(12.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                // Nombre
                Text(
                    text = dinosaur.name.replaceFirstChar { it.uppercase() },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Datos principales
                Text(
                    text = "📏 ${dinosaur.length}  ⚖️ ${dinosaur.weight}  📐 ${dinosaur.height}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                // Dieta y Período como chips
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        label = { Text(dinosaur.diet) },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (dinosaur.diet.contains("carnivore"))
                                Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                        )
                    )
                    AssistChip(
                        onClick = { },
                        label = { Text(dinosaur.period) }
                    )
                }
            }
        }
    }
}