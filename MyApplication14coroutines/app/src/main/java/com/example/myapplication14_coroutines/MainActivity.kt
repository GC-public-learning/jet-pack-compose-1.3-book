package com.example.myapplication14_coroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication14_coroutines.ui.theme.MyApplication14CoroutinesTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication14CoroutinesTheme {
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
    val coroutineScope = rememberCoroutineScope()
    var counter by remember { mutableStateOf(0) }
    val increaseCounter = { counter+=1 }

    val channel = Channel<Int>()

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch { performSlowTask2() }
    }
    SideEffect {
        coroutineScope.launch { performSlowTask3() }
    }

    Column() {
        // Mybutton continue to increase the text even if performSlowtask is running (see LogCat)
        MyButton(increaseCounter, coroutineScope)
        MyText(counter = counter)
        MyButton2(coroutineScope = coroutineScope, channel = channel)
    }
}

@Composable
fun MyButton(increaseCounter: ()-> Unit, coroutineScope: CoroutineScope) {
    Button(onClick = {
        coroutineScope.launch { performSlowTask() }
        increaseCounter()
    }) {
        Text(text = "Click me")
    }
}

@Composable
fun MyText(counter: Int){
    Text(text = counter.toString())
}

suspend fun performSlowTask() {
    println("performSlowTask before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask after")
}

// ----------------------------
// Channel
suspend fun performTask1(channel: Channel<Int>) {
    (1..6).forEach {
        channel.send(it) // it = numbers from 1 to 6
    }
}
suspend fun performTask2(channel: Channel<Int>) {
    repeat(6) {
        println("Received: ${ channel.receive() }")
    }
}

@Composable
fun MyButton2(coroutineScope: CoroutineScope, channel: Channel<Int>) {
    Button(onClick = {
        coroutineScope.launch() {
            coroutineScope.launch(Dispatchers.Main) { performTask1(channel) }
            coroutineScope.launch(Dispatchers.Main) { performTask2(channel) }
        }

    }) {
        Text(text = "Click me too")
    }
}

// ----------------------------
// Launched effect

suspend fun performSlowTask2() {
    println("performSlowTask 2 before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask 2 after")
}

suspend fun performSlowTask3() {
    println("performSlowTask 3 before") // see in Logcat > filter: System.out
    delay(5000) // simulates long-running task
    println("performSlowTask 3 after")
}




