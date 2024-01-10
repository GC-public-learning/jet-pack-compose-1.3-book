package com.example.myapplication26_flow

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication26_flow.ui.theme.MyApplication26FlowTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onSubscription
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
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
fun ScreenSetup(
    viewModel: DemoViewModel = viewModel(),
    viewModel2: DemoViewMOdel2 = viewModel(),
    viewModel3: DemoViewModel3 = viewModel()
) {
//    MainScreen(viewModel.myFlow)
    Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MainScreen(viewModel.newFlow3)
        MainScreen2(viewModel.newFlow3)
        MainScreen3(viewModel.newFlow3)
        MainScreen4(viewModel.myFlow)
        MainScreen5(viewModel)
        MainScreen6()
        MainScreen7(viewModel2)
        MainScreen8(viewModel3)
        MainScreen8(viewModel3)
    }
}
// COLD FLOW
// ---------------

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

@Composable
// Flow flattening > use of flow that use a flow
fun MainScreen5(viewModel: DemoViewModel = viewModel()) {
    var count by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        viewModel.myFlow
            //.flatMapConcat { viewModel.doubleIt(it) } // synchronous > both flows wait each other
            .flatMapMerge { viewModel.doubleIt(it) } // asynchronous > both flows don't wait each other
            .collect {
                count = it
                println("count = $it") // to check values not diplayed in the Text field
            }
    }
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

@SuppressLint("FlowOperatorInvokedInComposition")
@Composable
// Flows combination
fun MainScreen6() {
    var count by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        val flow1 = (1..5).asFlow().onEach { delay(1000) }
        val flow2 = flowOf("one", "two", "three", "four").onEach { delay(1500) }

        // zip : wait both flow have emitted before performing
        // combine : performs when one flow have emitted a value and user the former value of the flow not emitted
        flow1.zip(flow2) { value, string -> "$value, $string" }
            .collect { count = it }
    }
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
}

// -------------------------------------------------------------------------------

// HOT FLOW
// -----------

@Composable
// State flow
fun MainScreen7(viewModel: DemoViewMOdel2) {
    val count by viewModel.stateFlow.collectAsState()
    
    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    Button(onClick = { viewModel.increaseValue() }) {
        Text(text = "Click state FLow")
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
// Shared flow
fun MainScreen8(viewModel: DemoViewModel3) {
    val count by viewModel.sharedFlow.collectAsState(initial = 0)

    Text(text = "$count", style = TextStyle(fontSize = 40.sp))
    Button(onClick = { viewModel.startSharedFlow() }) {
        Text(text = "Click shared flow")
    }
    Text(text = "suscribers count = ${viewModel.subCount.value}")
}





// -------------------------------------------------------------------------------
@Preview
@Composable
fun ScreenSetupPreview() {
    MyApplication26FlowTheme {
        ScreenSetup(viewModel())

    }
}


