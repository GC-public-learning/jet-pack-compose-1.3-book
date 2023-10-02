package com.example.myapplication13_intrinsicsize

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication13_intrinsicsize.ui.theme.MyApplication13IntrinsicSizeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication13IntrinsicSizeTheme {
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
    var textState by remember { mutableStateOf("") }
    val onTextChange = { text: String -> textState = text }

    Column(
        Modifier
            .width(200.dp)
            .padding(5.dp)) {
//        Column(Modifier.width(IntrinsicSize.Max)) {
        Column(Modifier.width(IntrinsicSize.Min)) {
            // .Min > the biggest word of the textField defines the column width as long as all column children are smaller
            // .Max > the textField content defines the column width as long as all column children are smaller
            Text(
                text = textState,
                modifier = Modifier.padding(start = 4.dp)
            )
            Box(
                Modifier
                    .height(20.dp)
                    .fillMaxWidth() // as it takes the column width we can see the columne size in blue
                    .background(Color.Blue)
            )
            Box(Modifier
                .height(20.dp)
                .width(100.dp)
                .background(Color.Red))
        }
        MyTextField(text = textState, onTextChange = onTextChange)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTextField(text: String, onTextChange: (String) -> Unit) {
    TextField(
        value = text,
        onValueChange = onTextChange)
}