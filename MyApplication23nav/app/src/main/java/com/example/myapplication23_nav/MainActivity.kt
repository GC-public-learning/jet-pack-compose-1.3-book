package com.example.myapplication23_nav

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication23_nav.screens.Home
import com.example.myapplication23_nav.screens.Profile
import com.example.myapplication23_nav.screens.Welcome
import com.example.myapplication23_nav.ui.theme.MyApplication23NavTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication23NavTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = NavRoutes.home.route
    ) {
        composable(NavRoutes.home.route) {
            Home(navController = navController)
        }
        composable(NavRoutes.welcome.route + "/{userName}") {backStackEntry ->
            val userName = backStackEntry.arguments?.getString("userName")
            Welcome(navController = navController, userName)
        }
        composable(NavRoutes.profile.route) {
            Profile(navController = navController)
        }
    }
}

