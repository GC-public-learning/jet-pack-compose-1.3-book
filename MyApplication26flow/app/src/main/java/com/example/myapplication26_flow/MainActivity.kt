package com.example.myapplication26_flow

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.reduce
import kotlin.system.measureTimeMillis


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
    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainScreen(viewModel.newFlow3)
        MainScreen2(viewModel.newFlow3)
        MainScreen3(viewModel.newFlow3)
        MainScreen4(viewModel.myFlow)
    }
}

@Composable
// use of collectAsState
//fun MainScreen(flow: Flow<Int>) {
fun MainScreen(flow: Flow<String>) {
    // Consumer
//    val count by flow.collectAsState(initial = 0)
    val count by flow.collectAsState(initial = "Current value =")

    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

@Composable
// Use of collect (more options than collectAsState)
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
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

@Composable
// use of measureTimeMillis to calculate the time of the flow receive
fun MainScreen3(flow: Flow<String>) {
    var count by remember { mutableStateOf("Current value =") }

    LaunchedEffect(Unit) {
        val elapsedTime = measureTimeMillis {
            flow
                .buffer() // the producer emit without waiting the consumer on each iteration & all data received
                .collect {
                    count = it
                    delay(1000)
            }
        }
        count = "Duration = $elapsedTime" // affected once the flow is completed
    }
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

@Composable
// use of reduce()
fun MainScreen4(flow: Flow<Int>) {
    var count by remember { mutableStateOf<Int>(0) }

    LaunchedEffect(Unit) {
        flow.reduce { accumulator, value ->
            count = accumulator
            accumulator + value
        }
    }
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

@Preview
@Composable
fun ScreenSetupPreview() {
    MyApplication26FlowTheme {
        ScreenSetup(viewModel())
    }
}


