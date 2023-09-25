package com.example.myapplication11_customlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication11_customlayout.ui.theme.MyApplication11CustomLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication11CustomLayoutTheme {
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
    Box{
        CascadeLayout(spacing = 20){
            Box(modifier = Modifier
                .size(60.dp)
                .background(Color.Blue))
            Box(modifier = Modifier
                .size(80.dp, 40.dp)
                .background(Color.Red))
            Box(modifier = Modifier
                .size(90.dp, 100.dp)
                .background(Color.Cyan))
            Box(modifier = Modifier
                .size(50.dp)
                .background(Color.Magenta))
            Box(modifier = Modifier
                .size(70.dp)
                .background(Color.Green))
        }
    }
}

@Composable
fun CascadeLayout(
    modifier: Modifier = Modifier,
    spacing: Int = 0,
    content: @Composable () -> Unit
) {
    Layout(
        modifier = modifier,
        content = content
    ) {measurables, constraints ->
        layout(constraints.maxWidth, constraints.maxHeight) {
            var indent = 0
            var yCoord = 0
            val placeables = measurables.map { measurable ->
                measurable.measure(constraints)
            }
            placeables.forEach { placeable ->
                placeable.placeRelative(x = indent, y = yCoord)
                indent+=placeable.width + spacing
                yCoord +=placeable.height + spacing
            }
        }
    }
}

// ----------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}


