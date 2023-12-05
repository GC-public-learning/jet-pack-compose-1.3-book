package com.example.myapplication23_nav

sealed class NavRoutes(val route : String) {
    object home : NavRoutes("home")
    object Welcome : NavRoutes("welcome")
    object profile : NavRoutes("profile")
}
