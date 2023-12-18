package com.example.myapplication24_bottomnavbar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.myapplication24_bottomnavbar.screens.Contacts
import com.example.myapplication24_bottomnavbar.screens.Favorites
import com.example.myapplication24_bottomnavbar.screens.Home
import com.example.myapplication24_bottomnavbar.ui.theme.MyApplication24BottomNavBarTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication24BottomNavBarTheme {
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

@OptIn(ExperimentalMaterial3Api::class)
@androidx.compose.runtime.Composable
fun MainScreen() {
    val navController = rememberNavController()
    
    Scaffold(
        topBar = { TopAppBar(title = { Text(text = "Bottom nav Demo") }) },
        content = { padding -> // padding mandatory in scaffold
            Column(Modifier.padding(padding)) {
                NavigationHost(navController = navController)
            }
        },
        bottomBar = { BottomNavigationBar(navController = navController) }
    )
}

@Composable
fun NavigationHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.home.route
    ) {
        composable(NavRoutes.home.route) {
            Home()
        }
        composable(NavRoutes.contacts.route) {
            Contacts()
        }
        composable(NavRoutes.favorites.route) {
            Favorites()
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    NavigationBar {
        val backStackEntry by navController.currentBackStackEntryAsState() // to create currentRoute
        val currentRoute = backStackEntry?.destination?.route // to target the selected Items

        NavBarItems.BarItems.forEach { navItem ->
            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    navController.navigate(navItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true // combined with "restoreState" to get back the state when destination reached again
                        }
                        launchSingleTop = true // reuse the screen on the back stack top (no new instance)
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = navItem.image,
                        contentDescription = navItem.title
                    )
                },
                label = { Text(text = navItem.title)}
            )
        }
    }
}
