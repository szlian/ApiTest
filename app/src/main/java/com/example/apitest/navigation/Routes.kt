package com.example.apitest.navigation

sealed class Routes (val route: String) {
    data object DinoDetails:Routes("DinoDetails")
    data object DinoList:Routes("DinoList")
}
