package com.example.myapplication26_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication26_flow.ui.theme.MyApplication26FlowTheme
import kotlinx.coroutines.flow.Flow


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication26FlowTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
//    MainScreen(viewModel.myFlow)
    Column {
        MainScreen(viewModel.newFlow3)
        MainScreen2(viewModel.newFlow3)
    }
}

@Composable
// use of collectAsState
//fun MainScreen(flow: Flow<Int>) {
fun MainScreen(flow: Flow<String>) {
    // Consumer
//    val count by flow.collectAsState(initial = 0)
    val count by flow.collectAsState(initial = "Current value =")

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // use of consumer
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    }
}

@Composable
// Use of collect (more options than collectAsState
fun MainScreen2(flow: Flow<String>) {
    var count by remember { mutableStateOf("Current value =") }

    LaunchedEffect(Unit) {

        try {
            flow.collect {
                count = it
            }
        } finally {
            count = "Flow stream ended !"
        }
    }
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // use of consumer
        Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    }
}

@Preview
@Composable
fun ScreenSetupPreview() {
    MyApplication26FlowTheme {
        ScreenSetup(viewModel())
    }
}


