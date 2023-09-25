package com.example.myapplication10_layoutmodifier

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import com.example.myapplication10_layoutmodifier.ui.theme.MyApplication10LayoutModifierTheme
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication10LayoutModifierTheme {
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
    Box(modifier = Modifier.size(120.dp, 80.dp)){
        var y = 0
        val increasY = { value : Int -> y += value; y }
        ColorBox(
            Modifier.exampleLayout(0f).background(Color.Blue)
        )
        ColorBox(
            Modifier.exampleLayout(0.25f, increasY).background(Color.Green)
        )
        ColorBox(
            Modifier.exampleLayout(0.5f, increasY).background(Color.Yellow)
        )
        ColorBox(
            Modifier.exampleLayout(0.25f, increasY).background(Color.Red)
        )
        ColorBox(
            Modifier.exampleLayout(0f, increasY).background(Color.Magenta)
        )

    }
}

@Composable
fun ColorBox(modifier: Modifier) {
    Box(Modifier
            .padding(1.dp)
            .size(width = 40.dp, height = 10.dp)
            .then(modifier)
    )
}

//fun Modifier.exampleLayout(x: Int, y:Int) : Modifier {
//    return layout {measurable, constraints ->
//        val placeable = measurable.measure(constraints)
//        layout(placeable.width, placeable.height) {
//            placeable.placeRelative(x, y)
//        }
//    }
//}


//fun Modifier.exampleLayout(x:Int, y:Int) // shorter way
fun Modifier.exampleLayout(fraction: Float, increaseY : (Int) -> Int = {value : Int -> 0})
        = layout {measurable, constraints ->
        val placeable = measurable.measure(constraints)
        val x = (placeable.width * fraction).roundToInt()


        layout(placeable.width, placeable.height) {
            placeable.placeRelative(x = x, y = increaseY(placeable.height))
        }
    }

// -------------------------------------------------------
@Preview(showBackground = true, showSystemUi = false)
@Composable
fun MainScreenPreview() {
    MyApplication10LayoutModifierTheme {
        MainScreen()
    }

}