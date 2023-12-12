package com.example.myapplication24_bottomnavbar

sealed class NavRoutes(val route: String) {
    object home : NavRoutes("home")
    object contacts : NavRoutes("contacts")
    object favorites : NavRoutes("favorites")
}