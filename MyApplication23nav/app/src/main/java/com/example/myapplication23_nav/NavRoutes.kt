package com.example.myapplication23_nav

sealed class NavRoutes(val route : String) {
    object home : NavRoutes("home") // affectation done with : instead =
    object welcome : NavRoutes("welcome")
    object profile : NavRoutes("profile")
}
