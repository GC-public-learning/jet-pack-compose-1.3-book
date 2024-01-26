package com.example.myapplication28_inapppurchase

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication28_inapppurchase.ui.theme.MyApplication28InAppPurchaseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication28InAppPurchaseTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val purchaseHelper = PurchaseHelper(this)
                    purchaseHelper.billingSetup()
                    MainScreen(purchaseHelper)
                }
            }
        }
    }
}

@Composable
fun MainScreen(purchaseHelper: PurchaseHelper) {
    val buyEnabled by purchaseHelper.buyEnabled.collectAsState(false)
    val consumeEnabled by purchaseHelper.consumeEnabled.collectAsState(false)
    val productName by purchaseHelper.productName.collectAsState("")
    val statusText by purchaseHelper.statusText.collectAsState("")

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = productName,
            modifier = Modifier.padding(20.dp),
            fontSize = 30.sp
        )
        Text(text = statusText)
        Row(Modifier.padding(20.dp)) {
            Button(
                onClick = { purchaseHelper.makePurchase() },
                modifier = Modifier.padding(20.dp),
                enabled = buyEnabled
            ) {
                Text(text = "Purchase")
            }
            Button(
                onClick = { purchaseHelper.consumePurchase() },
                modifier = Modifier.padding(20.dp),
                enabled = consumeEnabled
            ) {
                Text("Consume")
            }
        }
    }
}


