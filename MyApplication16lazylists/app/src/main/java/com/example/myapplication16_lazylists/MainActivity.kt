package com.example.myapplication16_lazylists

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.myapplication16_lazylists.ui.theme.MyApplication16LazyListsTheme

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

@Composable
fun MainScreen(itemArray: Array<out String>) {
    val context = LocalContext.current // in order to generate a toast in the good screen

    val onListItemClick = { text : String ->
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }
    LazyColumn{
        items(itemArray) {model ->
            MyListItem(
                item = model,
                onItemClick = onListItemClick
                )
        }
    }
}

@Composable
fun ImageLoader(item: String) {
    val url = "https://www.ebookfrenzy.com/book_examples/car_logos/" + item.
    substringBefore(" ") + "_logo.png"
    Image(
        painter = rememberAsyncImagePainter(url),
        contentDescription = "car image",
        contentScale = ContentScale.Fit,
        modifier = Modifier.size(75.dp)
    )
}

@Composable
fun MyListItem(item: String, onItemClick:(String) -> Unit) {
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

@Preview
@Composable
fun GreetingPreview() {
    val itemArray: Array<String> = arrayOf( // mock array
        "Cadillac Eldorado",
        "Ford Fairline",
        "Plymouth Fury"
    )
    MyApplication16LazyListsTheme {
        MainScreen(itemArray = itemArray)
    }
}
