package com.example.myapplication9_box

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication9_box.ui.theme.MyApplication9BoxTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication9BoxTheme {
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
    Column {
        Box(
            contentAlignment = Alignment.CenterEnd,
            modifier = Modifier
                .size(400.dp, 400.dp)
                .background(color = Color.Gray)
        ) {
            val height = 200.dp
            val width = 200.dp
            for (i in 0..2)
                TextCell(
                    text = "${i + 1}",
                    Modifier.size(width = width, height = height),
                    color = if(i==0) Color.Blue else if(i==1) Color.Red else Color.Green
                )
        }
        Box(modifier = Modifier.size(height = 90.dp, width = 290.dp).background(Color.Yellow)) {
            Text("TopStart", Modifier.align(Alignment.TopStart))
            Text("TopCenter", Modifier.align(Alignment.TopCenter))
            Text("TopEnd", Modifier.align(Alignment.TopEnd))
            Text("CenterStart", Modifier.align(Alignment.CenterStart))
            Text("Center", Modifier.align(Alignment.Center))
            Text(text = "CenterEnd", Modifier.align(Alignment.CenterEnd))
            Text("BottomStart", Modifier.align(Alignment.BottomStart))
            Text("BottomCenter", Modifier.align(Alignment.BottomCenter))
            Text("BottomEnd", Modifier.align(Alignment.BottomEnd))
        }
        Row {
            Box(
                Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .background(Color.Blue))
            Spacer(modifier = Modifier.width(10.dp))
            Box(
                Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color.Red))
        }
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            Modifier
                .size(150.dp)
                .clip(CutCornerShape(20.dp))
                .background(Color.Green))
    }
}

@Composable
fun TextCell(text: String, modifier: Modifier = Modifier, fontSize: Int = 150, color: Color) {
    val cellModifier = Modifier
        .padding(4.dp)
        .border(width = 5.dp, color = Color.Black)
    Text(
        text = text,
        cellModifier.then(modifier),
        fontSize = fontSize.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplication9BoxTheme {
        MainScreen()
    }
}

