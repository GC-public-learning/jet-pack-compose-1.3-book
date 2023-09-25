package com.example.myapplication4complocal

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication4complocal.ui.theme.MyApplication4CompLocalTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication4CompLocalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Comp1()
                }
            }
        }
    }
}
val LocalColor = staticCompositionLocalOf { Color.Gray }
@androidx.compose.runtime.Composable
fun Comp1 () {
    // no ternary operator in kotlin but if else can be used like that ->
    val color = if(isSystemInDarkTheme()) Color.Blue else Color(0xFFffdbcf)
    Column {
        Comp2()
        CompositionLocalProvider(LocalColor provides color) {
            // complocal modified for values that use it after this stage > color
            Comp3()
        }
    }
}

@androidx.compose.runtime.Composable
fun Comp2 () {
    Comp4()
}

@androidx.compose.runtime.Composable
fun Comp3 () {
    Text(
        text = "Comp 3",
        modifier = Modifier.background(LocalColor.current)
    )
    CompositionLocalProvider(LocalColor provides Color.Red) {
        // complocal modified for values that use it after this stage > Red
        Comp5()
    }
}

@Composable
fun Comp4 () {
    Comp6()
}

@Composable
fun Comp5() {
    Text(
        text = "Comp 5",
        modifier = Modifier.background(LocalColor.current)
    )
    CompositionLocalProvider(LocalColor provides Color.Green) {
        Comp7()
    }
    CompositionLocalProvider(LocalColor provides Color.Yellow) {
        Comp8()
    }

}

@Composable
fun Comp6() {
    Text(
        text = "Comp 6",
        modifier = Modifier.background(LocalColor.current)
    )
}

@Composable
fun Comp7() {
    Text(
        text = "Comp 7",
        modifier = Modifier.background(LocalColor.current)
    )
}

@Composable
fun Comp8() {
    Text(
        text = "Comp 8",
        modifier = Modifier.background(LocalColor.current)
    )
}

// -----------------------------------------------------------------
@Preview(showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun DarkPrev() {
    MyApplication4CompLocalTheme {
        Comp1()
    }
}