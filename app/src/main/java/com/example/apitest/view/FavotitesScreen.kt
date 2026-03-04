package com.example.apitest.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.apitest.DinosaurCard
import com.example.apitest.viewmodel.DinoViewModel

@Composable
fun FavoritesScreen(
    viewModel: DinoViewModel, // Recibe el ViewModel como parámetro
    onDinoClick: (String) -> Unit
) {
    val favoriteDinos by viewModel.favoriteDinos.collectAsState()
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        if (favoriteIds.isEmpty()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Favorite,
                    contentDescription = null,
                    modifier = Modifier.size(80.dp),
                    tint = Color.LightGray
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "No tienes dinosaurios favoritos",
                    style = MaterialTheme.typography.titleLarge,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
                Text(
                    text = "Toca el corazón en cualquier dinosaurio para guardarlo aquí",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = Color.Gray,
                    modifier = Modifier.padding(horizontal = 32.dp, vertical = 8.dp)
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(favoriteDinos) { dinosaur ->
                    DinosaurCard(
                        dinosaur = dinosaur,
                        isFavorite = true,
                        onFavoriteClick = { viewModel.toggleFavorite(dinosaur.id) },
                        onClick = { onDinoClick(dinosaur.id) }
                    )
                }
            }
        }
    }
}