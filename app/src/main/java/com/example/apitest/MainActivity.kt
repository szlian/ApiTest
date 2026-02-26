package com.example.apitest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.apitest.navigation.Routes
import com.example.apitest.viewModel.DinoViewModel

class MainActivity : ComponentActivity() {

    private val viewModel: DinoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            val navigationController = rememberNavController()
            NavHost(
                navController = navigationController,  {
                startDestination = Routes.DinoList.route
            }
                ) {
                composable(Routes.DinoList.route) { Routes.DinoList(navController = navigationController) }
                composable(Routes.DinoDetails.route) { Routes.DinoDetails(navController = navigationController) }
            }
        }
    }
}
