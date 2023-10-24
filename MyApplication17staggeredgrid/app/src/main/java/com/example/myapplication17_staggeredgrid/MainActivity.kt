package com.example.myapplication17_staggeredgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication17_staggeredgrid.ui.theme.MyApplication17StaggeredGridTheme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication17StaggeredGridTheme {
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
    val items = (1 .. 50).map {
        BoxProperties(
            side = Random.nextInt(50, 200).dp,
            color = Color(
                Random.nextInt(255),
                Random.nextInt(255),
                Random.nextInt(255),
                255
            )
        )
    }
    Column {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Fixed(3),
            modifier = Modifier.weight(0.5f),
            contentPadding = PaddingValues(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalItemSpacing = 8.dp
        ) {
            items(items) { values ->
                GridItem(properties = values, false) // false for vertical
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        LazyHorizontalStaggeredGrid(
            rows = StaggeredGridCells.Adaptive(minSize = 50.dp),
            // mandatory to evenly spread the available space between the sides of the items
            // with at least such minSize value
            modifier = Modifier.weight(0.5f),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalItemSpacing = 8.dp
        ) {
            items(items) { values ->
                GridItem(properties = values, true) // true for horizontal
            }
        }
    }
}

@Composable
fun GridItem(properties: BoxProperties, kind: Boolean) { // true: horizontal, false: vertical
    var modifier = if(kind) Modifier
        .fillMaxHeight() // not mandatory
        .width(properties.side)
    else Modifier
        .fillMaxWidth() // not mandatory
        .height(properties.side)
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(properties.color)
            .then(modifier)
    )
}

// ---------------------------

data class BoxProperties(
    val color: Color,
    val side: Dp
)

// ---------------------------

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplication17StaggeredGridTheme {
        MainScreen()
    }
}