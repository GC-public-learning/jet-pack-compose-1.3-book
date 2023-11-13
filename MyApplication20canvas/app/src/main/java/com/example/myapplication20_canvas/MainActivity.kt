package com.example.myapplication20_canvas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication20_canvas.ui.theme.MyApplication20CanvasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication20CanvasTheme {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen() {
    Box() {
        Box(Modifier.width(303.dp)) {
            LazyVerticalGrid(
                // workaround Max width > wrap into Box into Box
                columns = GridCells.Adaptive(150.dp),
                modifier = Modifier
                    .background(Color.Yellow),
                contentPadding = PaddingValues(1.dp, 1.dp, 1.dp, 1.dp)
            ) {
                items(5) { value ->
                    Box(
                        Modifier
                            .background(Color.Yellow)
                    ) {
                        when (value) {
                            0 -> DrawLine()
                            1 -> DrawRect()
                            2 -> DrawCircle()
                            3 -> DrawOval()
                            4 -> GradientFill()
                        }
                    }

                }

            }
        }
    }
}

@Composable
fun DrawLine() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .background(Color.Gray)
    ) {
        val height = size.height
        val width = size.width

        drawLine(
            start = Offset(x = 0f, y = 0f),
            end = Offset(x = width, y = height),
            color = Color.Blue,
            strokeWidth = 16f,
            pathEffect = PathEffect.dashPathEffect(
                floatArrayOf(30f, 10f, 10f, 10f), phase = 0f
            )
        )
    }
}

@Composable
fun DrawRect() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .background(Color.DarkGray)
    ) {
        val sizeRectangle = Size(width = size.width / 2, height = 100.dp.toPx())
        inset(100f, 200f) {
            // ! the width and height size values of the canvas are modified in function of the inset !
            // that's why  the inset is not added in the formula to center the rectangle
//            drawRect(
            rotate(45f) {
                drawRoundRect(
                    color = Color.Red,
                    size = sizeRectangle,
                    topLeft = Offset(
                        x = (size.width - sizeRectangle.width) / 2,
                        y = (size.height - sizeRectangle.height) / 2
                    ),
                    style = Stroke(width = 6.dp.toPx()), // stroke : not filled
                    cornerRadius = CornerRadius(30.dp.toPx(), 30.dp.toPx())
                )
            }
        }
    }
}

@Composable
fun DrawCircle() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .background(Color.White)
    ) {
        drawCircle(
            color = Color.Magenta,
            center = center,
            radius = 50.dp.toPx()
        )
    }
}

@Composable
fun DrawOval() {
    Canvas(
        modifier = Modifier
            .size(150.dp)
            .background(Color.LightGray)
    ) {
        drawOval(
            color = Color.Green,
            topLeft = Offset(x = 25.dp.toPx(), y = 90.dp.toPx()),
            size = Size(
                width = size.width - 50.dp.toPx(),
                height = size.height / 2 - 50.dp.toPx()
            ),
            style = Stroke(width = 12.dp.toPx())

        )
    }
}

@Composable
fun GradientFill() {
    Canvas(modifier = Modifier.size(150.dp)) {
        val canvasSize = size
        val colorList: List<Color> = listOf(Color.Red, Color.Yellow, Color.Blue, Color.Magenta)

        val brush = Brush.horizontalGradient(
            colors = colorList,
            startX = 0f,
            endX = 150.dp.toPx(),
            tileMode = TileMode.Repeated
        )
        drawRect(
            brush = brush,
            size = canvasSize
        )
    }
}
// -------------------------------------

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplication20CanvasTheme {
        MainScreen()
    }
}

