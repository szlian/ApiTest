package com.example.apitest.navigation

sealed class Routes(val route: String) {
    data object DinoList : Routes("dinoList")
    data object DinoDetails : Routes("dinoDetails/{dinoId}") {
        fun createRoute(dinoId: String) = "dinoDetails/$dinoId"
    }
    data object Favorites : Routes("favorites")
}