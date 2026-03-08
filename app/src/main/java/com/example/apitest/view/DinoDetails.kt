package com.example.apitest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.apitest.cleanText
import com.example.apitest.viewmodel.DinoViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DinoDetails(
    dinoId: String,
    viewModel: DinoViewModel,
    backgroundColor: Color, // Nuevo parámetro
    onBackClick: () -> Unit
) {
    val allDinos by viewModel.dinos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    val dinosaur = remember(dinoId, allDinos) {
        allDinos.find { it.id == dinoId }
    }

    val isFavorite = favoriteIds.contains(dinoId)

    Scaffold(
        containerColor = backgroundColor, // Fondo de la pantalla
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = dinosaur?.name?.cleanText()?.replaceFirstChar { it.uppercase() } ?: "Cargando...",
                        maxLines = 1
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                actions = {
                    if (dinosaur != null) {
                        IconButton(onClick = { viewModel.toggleFavorite(dinoId) }) {
                            Icon(
                                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos",
                                tint = if (isFavorite) Color.Red else Color.White
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = backgroundColor,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading && dinosaur == null -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                dinosaur == null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Dinosaurio no encontrado",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Button(onClick = onBackClick) {
                            Text("Volver")
                        }
                    }
                }
                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                    ) {
                        dinosaur.image?.let { url ->
                            AsyncImage(
                                model = url,
                                contentDescription = "Imagen de ${dinosaur.name}",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(280.dp),
                                contentScale = ContentScale.Crop
                            )
                        }

                        Column(
                            modifier = Modifier.padding(20.dp)
                        ) {
                            Text(
                                text = dinosaur.name.cleanText().replaceFirstChar { it.uppercase() },
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )

                            if (!dinosaur.type.isNullOrEmpty()) {
                                Text(
                                    text = dinosaur.type.cleanText().replaceFirstChar { it.uppercase() },
                                    fontSize = 18.sp,
                                    color = MaterialTheme.colorScheme.secondary,
                                    modifier = Modifier.padding(top = 4.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                val dietColor = when {
                                    dinosaur.diet.contains("carnivore", ignoreCase = true) -> Color(0xFFFFCDD2)
                                    dinosaur.diet.contains("herbivore", ignoreCase = true) -> Color(0xFFC8E6C9)
                                    else -> Color(0xFFFFF9C4)
                                }

                                AssistChip(
                                    onClick = { },
                                    label = {
                                        Text(
                                            dinosaur.diet.cleanText().replaceFirstChar { it.uppercase() },
                                            maxLines = 1
                                        )
                                    },
                                    colors = AssistChipDefaults.assistChipColors(containerColor = dietColor)
                                )

                                AssistChip(
                                    onClick = { },
                                    label = {
                                        Text(
                                            dinosaur.period.cleanText().replaceFirstChar { it.uppercase() },
                                            maxLines = 1
                                        )
                                    }
                                )
                            }

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Especificaciones",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            InfoRow(icon = "📏", label = "Longitud", value = dinosaur.length.cleanText())
                            InfoRow(icon = "⚖️", label = "Peso", value = dinosaur.weight.cleanText())
                            InfoRow(icon = "📐", label = "Altura", value = dinosaur.height.cleanText())
                            InfoRow(icon = "🌍", label = "Región", value = dinosaur.region.cleanText())
                            InfoRow(icon = "⏳", label = "Existió", value = dinosaur.existed.cleanText())

                            Spacer(modifier = Modifier.height(24.dp))

                            Text(
                                text = "Descripción",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(bottom = 12.dp)
                            )

                            Card(
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f)
                                ),
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(
                                    text = dinosaur.description.cleanText(),
                                    fontSize = 16.sp,
                                    lineHeight = 24.sp,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(32.dp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun InfoRow(icon: String, label: String, value: String) {
    if (value.isBlank()) return

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = label,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
    Divider(modifier = Modifier.padding(top = 4.dp))
}