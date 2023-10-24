package com.example.myapplication16_lazylists

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication16_lazylists.ui.theme.MyApplication16LazyListsTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private var itemArray: Array<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {

        itemArray = resources.getStringArray(R.array.car_array) // call of the array string xml

        super.onCreate(savedInstanceState)
        setContent {
            MyApplication16LazyListsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(itemArray = itemArray as Array<out String>)
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(itemArray: Array<out String>) {
    val context = LocalContext.current // in order to generate a toast in the good screen
    val groupedItems = itemArray.groupBy { it.substringBefore(' ') }

    val listState = rememberLazyListState()
    val coroutintScope = rememberCoroutineScope()
    val displayButton = listState.firstVisibleItemIndex > 5

    val onListItemClick = { text: String ->
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
    Box {
        LazyColumn(
            state = listState,
            contentPadding = PaddingValues(bottom = 50.dp) // bottom : padding at the end of the entire list
        ) {
            groupedItems.forEach { manufacturer, models ->
                stickyHeader {
                    Text(
                        text = manufacturer,
                        color = Color.White,
                        modifier = Modifier
                            .background(Color.Gray)
                            .padding(5.dp)
                            .fillMaxWidth()
                    )
                }
                items(models) { model ->
                    MyListItem(
                        item = model,
                        onItemClick = onListItemClick
                    )
                }
            }
        }
        AnimatedVisibility(
            visible = displayButton,
            Modifier.align(Alignment.BottomCenter)
        ) {
            OutlinedButton(
                onClick = {
                    coroutintScope.launch { listState.scrollToItem(0) }
                },
                border = BorderStroke(1.dp, Color.Gray),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.DarkGray),
                modifier = Modifier.padding(5.dp)
            ) {
                Text(text = "Top")
            }
        }

    }
}

@Composable
fun ImageLoader(item: String) {
    val url =
        "https://www.ebookfrenzy.com/book_examples/car_logos/" + item.substringBefore(" ") + "_logo.png"
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "car image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(75.dp)
    )
}

@Composable
fun MyListItem(item: String, onItemClick: (String) -> Unit) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        ),
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onItemClick(item) },
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            ImageLoader(item = item)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = item,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val itemArray: Array<String> = arrayOf( // mock array
        "Cadillac Eldorado",
        "Cadillac ElFuego",
        "Ford Fairline",
        "Ford Mustang",
        "Plymouth Fury",
        "Plymouth Quiet"
    )
    MyApplication16LazyListsTheme {
        MainScreen(itemArray = itemArray)
    }
}
