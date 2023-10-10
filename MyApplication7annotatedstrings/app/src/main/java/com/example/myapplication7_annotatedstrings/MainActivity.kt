package com.example.myapplication7_annotatedstrings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.myapplication7_annotatedstrings.ui.theme.MyApplication7AnnotatedStringsTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication7AnnotatedStringsTheme {
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

@androidx.compose.runtime.Composable
fun MainScreen() {
    Column {
        SpanString()
        ParaString()
        BrushStyle()
    }
}

@Composable
fun SpanString() {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 30.sp
                )
            ){
                append("T")
            }
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("his")
            }
            append(" is ")
            withStyle(style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                color = Color.Blue
                )
            ) {
                append("great !")
            }
        }
    )
}

@Composable
fun ParaString() {
    Text(text = buildAnnotatedString {
        append("\nThis is some text without style applied to it.\n")
        withStyle(style = ParagraphStyle(
            lineHeight = 30.sp,
            textIndent = TextIndent(
                firstLine = 50.sp, // setup the gap on the left of the 1st line
                restLine = 20.sp // setup the gap on the left for the rest of the lines
            ))
        ) {
            append("""This is some text that is indented more on the first lines
                |than the rest of the lines. It also has an increased line height.""".trimMargin())

        } // With .triMargin(), when """ """ is used "|" has to be used on each other lines 
        // to not take in account the spaces before
        withStyle(style = ParagraphStyle(textAlign = TextAlign.End)) {
            append("text that is aligned on the right.")
        }
    })
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun BrushStyle() {
    val colorList: List<Color> = listOf(Color.Red, Color.Blue, Color.Magenta, Color.Yellow, Color.Red)
    Text(text = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 70.sp,
                brush = Brush.linearGradient(colors = colorList)
            )
        ){
            append("COMPOSE !")
        }
    })


}

// -----------------------------
@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun spanStylePreview() {
    MyApplication7AnnotatedStringsTheme {
        MainScreen()
    }

}



