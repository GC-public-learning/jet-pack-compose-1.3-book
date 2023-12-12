package com.example.myapplication23_nav.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication23_nav.NavRoutes

@Composable
fun Welcome(navController: NavHostController, userName: String?) {
    val un = userName
    un ?: ""
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Welcome $userName",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.size(30.dp))
            Button(onClick = {
                    navController.navigate(NavRoutes.profile.route) {
                        popUpTo(NavRoutes.home.route) // the back stack will ignore all routes but the Home
                    }
                }
            ) {
                Text(text = "Set up your Profile")
            }
        }
    }
}