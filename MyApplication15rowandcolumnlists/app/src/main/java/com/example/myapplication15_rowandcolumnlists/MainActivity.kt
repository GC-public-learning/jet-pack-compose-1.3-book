package com.example.myapplication15_rowandcolumnlists

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication15_rowandcolumnlists.ui.theme.MyApplication15RowAndColumnListsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication15RowAndColumnListsTheme {
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
        RowList()
        ColumnList()
    }
}

@Composable
fun ColumnList() {
    val scrollState = rememberScrollState() // scrollState
    val coroutineScope = rememberCoroutineScope() // to setup special options for the scroll list

    Column {
        Row {
            Button(
                onClick = { coroutineScope.launch { scrollState.animateScrollTo(0) } },
                modifier = Modifier
//                    .fillMaxWidth(0.5f)
                    .weight(0.5f)
                    // other way to use the available space > each brother's size is setup in function of the other ones
                    .padding(2.dp)
            ) {
                Text(text = "Top")
            }
            Button(
                onClick = { coroutineScope.launch { scrollState.animateScrollTo(scrollState.maxValue) } },
                modifier = Modifier
                    .weight(0.5f)
                    .padding(2.dp)
            ) {
                Text(text = "End")
            }
        }
        Column(Modifier.verticalScroll(scrollState)) { // use of scrollState
            repeat(500) {
                Text(
                    text = "Item $it",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(5.dp)
                )
            }
        }
    }
}

@Composable
fun RowList() {
    val scrollState = rememberScrollState()
    Row(
        Modifier.horizontalScroll(scrollState)
    ) {
        repeat(50) {
            Text(
                text = "$it",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(5.dp)
            )

        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MyApplication15RowAndColumnListsTheme {
        MainScreen()
    }
}