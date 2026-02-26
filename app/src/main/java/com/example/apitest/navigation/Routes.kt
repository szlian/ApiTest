package com.example.apitest.navigation

sealed class Routes(val route: String) {
    object DinoDetails:Routes("DinoDetails")
    object DinoList:Routes("DinoList")
}
