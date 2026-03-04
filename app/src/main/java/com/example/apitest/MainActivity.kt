package com.example.apitest

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.apitest.model.Dinosaur
import com.example.apitest.navigation.Routes
import com.example.apitest.ui.theme.GreenJC
import com.example.apitest.view.DinoDetails
import com.example.apitest.view.DinoList
import com.example.apitest.view.FavoritesScreen
import com.example.apitest.viewmodel.DinoViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                // ViewModel se crea aquí para compartirlo con todas las pantallas
                val viewModel: DinoViewModel = viewModel()
                MyBottomAppBar(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomAppBar(viewModel: DinoViewModel) {
    val navigationController = rememberNavController()
    val context = LocalContext.current.applicationContext
    val favoriteIds by viewModel.favoriteIds.collectAsState()

    val navBackStackEntry by navigationController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val selectedRoute = when {
        currentRoute?.startsWith("dinoDetails") == true -> Routes.DinoList.route
        currentRoute == Routes.Favorites.route -> Routes.Favorites.route
        else -> Routes.DinoList.route
    }

    Scaffold(
        topBar = {
            if (currentRoute == Routes.DinoList.route || currentRoute == Routes.Favorites.route) {
                TopAppBar(
                    title = {
                        Text(
                            when (currentRoute) {
                                Routes.Favorites.route -> "Mis Favoritos ❤️"
                                else -> "🦕 DinoAPI Explorer"
                            }
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = GreenJC,
                        titleContentColor = Color.White
                    )
                )
            }
        },
        bottomBar = {
            BottomAppBar(
                containerColor = GreenJC
            ) {
                IconButton(
                    onClick = {
                        navigationController.navigate(Routes.DinoList.route) {
                            popUpTo(Routes.DinoList.route) { inclusive = true }
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

                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    FloatingActionButton(
                        onClick = {
                            Toast.makeText(context, "${favoriteIds.size} favoritos", Toast.LENGTH_SHORT).show()
                        },
                        containerColor = Color.White
                    ) {
                        BadgedBox(
                            badge = {
                                if (favoriteIds.isNotEmpty()) {
                                    Badge { Text(favoriteIds.size.toString()) }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favoritos",
                                tint = if (favoriteIds.isNotEmpty()) Color.Red else GreenJC
                            )
                        }
                    }
                }

                IconButton(
                    onClick = {
                        navigationController.navigate(Routes.Favorites.route) {
                            popUpTo(Routes.DinoList.route)
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    BadgedBox(
                        badge = {
                            if (favoriteIds.isNotEmpty()) {
                                Badge { Text(favoriteIds.size.toString()) }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favoritos",
                            tint = if (selectedRoute == Routes.Favorites.route) Color.Cyan else Color.White
                        )
                    }
                }
            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navigationController,
            startDestination = Routes.DinoList.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(Routes.DinoList.route) {
                DinoList(
                    viewModel = viewModel,
                    onDinoClick = { dinoId ->
                        navigationController.navigate(Routes.DinoDetails.createRoute(dinoId))
                    }
                )
            }

            composable(
                route = Routes.DinoDetails.route,
                arguments = listOf(navArgument("dinoId") { type = NavType.StringType })
            ) { backStackEntry ->
                val dinoId = backStackEntry.arguments?.getString("dinoId") ?: ""
                DinoDetails(
                    viewModel = viewModel,
                    dinoId = dinoId,
                    onBackClick = {
                        navigationController.popBackStack()
                    }
                )
            }

            composable(Routes.Favorites.route) {
                FavoritesScreen(
                    viewModel = viewModel,
                    onDinoClick = { dinoId ->
                        navigationController.navigate(Routes.DinoDetails.createRoute(dinoId))
                    }
                )
            }
        }
    }
}

@Composable
fun DinosaurCard(
    dinosaur: Dinosaur,
    isFavorite: Boolean,
    onFavoriteClick: () -> Unit,
    onClick: () -> Unit
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                Text(
                    text = dinosaur.name.cleanText().replaceFirstChar { it.uppercase() },
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "📏 ${dinosaur.length.cleanText()}  ⚖️ ${dinosaur.weight.cleanText()}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = dinosaur.diet.cleanText().replaceFirstChar { it.uppercase() },
                                maxLines = 1
                            )
                        },
                        colors = AssistChipDefaults.assistChipColors(
                            containerColor = if (dinosaur.diet.contains("carnivore"))
                                Color(0xFFFFCDD2) else Color(0xFFC8E6C9)
                        )
                    )
                    AssistChip(
                        onClick = { },
                        label = {
                            Text(
                                text = dinosaur.period.cleanText().replaceFirstChar { it.uppercase() },
                                maxLines = 1
                            )
                        }
                    )
                }
            }

            IconButton(onClick = onFavoriteClick) {
                Icon(
                    imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavorite) "Quitar de favoritos" else "Añadir a favoritos",
                    tint = if (isFavorite) Color.Red else Color.Gray,
                    modifier = Modifier.size(28.dp)
                )
            }
        }
    }
}

// Extensión para limpiar texto
fun String?.cleanText(): String {
    return this?.replace("\n", " ")
        ?.replace("\r", " ")
        ?.replace("\\s+".toRegex(), " ")
        ?.trim()
        ?: ""
}