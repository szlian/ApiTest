package com.example.apitest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.apitest.DinosaurCard
import com.example.apitest.viewmodel.DinoViewModel


@Composable
fun DinoList(
    viewModel: DinoViewModel, // Recibe el ViewModel como parámetro
    onDinoClick: (String) -> Unit
) {
    val dinos by viewModel.dinos.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Error al cargar",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        text = error ?: "",
                        color = MaterialTheme.colorScheme.error
                    )
                }
            }
            dinos.isEmpty() -> {
                Text(
                    text = "No hay dinosaurios disponibles",
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(
                        items = dinos,
                        key = { it.id }
                    ) { dinosaur ->
                        DinosaurCard(
                            dinosaur = dinosaur,
                            isFavorite = favoriteIds.contains(dinosaur.id),
                            onFavoriteClick = { viewModel.toggleFavorite(dinosaur.id) },
                            onClick = { onDinoClick(dinosaur.id) }
                        )
                    }
                }
            }
        }
    }
}