package com.example.myapplication3_states

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication3_states.ui.theme.MyApplication3StatesTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication3StatesTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column {
                        DemoScreen()
                        FunA()
                    }
                }
            }
        }
    }
}

@Composable
fun DemoScreen() {
    MyTextField()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField() {
//    var textState = remember { mutableStateOf("") } // 1
//    var (textValue, setText) = remember { mutableStateOf("") } // 2
    // var textState by remember { mutableStateOf("") } // 3 : most common
    var textState by rememberSaveable { mutableStateOf("") } // 3.2 keep the states even after activity rebuilt
//    val onTextChange = {text : String -> textState.value = text} // 1
//    val onTextChange = { text: String -> // 2
//        setText(text)
//    }
    val onTextChange = {text : String -> textState = text} // 3


    TextField(
//        value = textState.value, // 1
//        value = textValue, // 2
        value = textState, // 3
        onValueChange = onTextChange
    )
}

@Composable
fun FunA() {
    var switchState by remember { mutableStateOf(true) }
    val onSwitchChange = {value : Boolean -> switchState = value} // no return, just assignation

    FunB(switchState = switchState, onSwitchChange = onSwitchChange)
}

@Composable
fun FunB(switchState : Boolean, onSwitchChange : (Boolean) -> Unit) {
    Switch(
        checked = switchState,
        onCheckedChange = onSwitchChange
    )
}



// -------------------------------------------------------

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplication3StatesTheme {
        Column {
            DemoScreen()
            FunA()
        }
    }
}