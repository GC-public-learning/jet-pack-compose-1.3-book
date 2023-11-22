package com.example.myapplication21_viewmodels

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication21_viewmodels.ui.theme.MyApplication21ViewmodelsTheme
import java.lang.Exception
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication21ViewmodelsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ScreenSetup()
                }
            }
        }
    }
}

@Composable
fun ScreenSetup(viewModel: DemoViewModel = viewModel()) {
    MainScreen(
        isFahrenheit = viewModel.isFahrenheit,
        result = viewModel.result,
        convertTemp = { viewModel.convertTemp(it) },
        switchChange = { viewModel.switchChange() },
        removeSpecialChars = { viewModel.removeSpecialChars(it) }
    )
}

@Composable
fun MainScreen(
    isFahrenheit: Boolean,
    result: String,
    convertTemp: (String) -> Unit,
    switchChange: () -> Unit,
    removeSpecialChars: (String) -> String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        var textState by remember { mutableStateOf("") }
        val onTextChange = { text: String, ->
            textState = removeSpecialChars(text)
            convertTemp(textState)
        }
        // with convertTemp() here the conversion is done in live

        Text(
            text = "Temp converter",
            modifier = Modifier.padding(20.dp),
            style = MaterialTheme.typography.headlineSmall
        )
        InputRow(
            isFahrenheit = isFahrenheit,
            textState = textState,
            onTextChange = onTextChange,
            switchChange = switchChange,
            convertTemp = convertTemp
        )
        Crossfade(
            targetState = !isFahrenheit,
            animationSpec = tween(2000), label = "crossFade"
        ) { visible ->
            when (visible) {
                true -> Text(
                    text = "$result \u2109",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(20.dp)
                )

                false -> Text(
                    text = "$result \u2103",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.padding(20.dp)
                )
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputRow(
    isFahrenheit: Boolean,
    textState: String,
    onTextChange: (String) -> Unit,
    switchChange: () -> Unit,
    convertTemp: (String) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Switch(
            checked = isFahrenheit,
            onCheckedChange = { switchChange(); convertTemp(textState) }
            // conversion also done when the switch is changed
        )
        OutlinedTextField(
            value = textState,
            onValueChange = { onTextChange(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number
            ),
            singleLine = true,
            label = { Text("Enter temperature") },
            modifier = Modifier.padding(10.dp),
            textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp),
            trailingIcon = {
                Icon(
                    painter = painterResource(R.drawable.baseline_ac_unit_24),
                    contentDescription = "frost",
                    modifier = Modifier.size(40.dp)
                )
            }
        )
        Crossfade(
            targetState = isFahrenheit,
            animationSpec = tween(2000), label = "crossFade"
        ) { visible ->
            when (visible) {
                true -> Text(
                    "\u2109", style = MaterialTheme.typography.headlineSmall,
                )

                false -> Text(
                    "\u2103", style = MaterialTheme.typography.headlineSmall
                )
            }

        }
    }
}

//--------------------------------------
class DemoViewModel : ViewModel() {
    var isFahrenheit by mutableStateOf(true)
    var result by mutableStateOf("")

    fun convertTemp(temp: String) {
        if(temp == "") { result = ""; return }
        result = try {
            val tempInt = temp.toInt()
            if (isFahrenheit) {
                ((tempInt - 32) * 0.5556).roundToInt().toString()
            } else {
                ((tempInt * 1.8) + 32).roundToInt().toString()
            }
        }
        catch (e: Exception) {
            "Invalid Entry"
        }
    }
    fun switchChange() {
        isFahrenheit = !isFahrenheit
    }
    fun removeSpecialChars(text: String): String {
        var result: String
        var firstChar = Regex("^.").find(text)?.value // capture 1st char
        if(firstChar != "-") firstChar = "" // if 1st char is not minus, 1st char deleted
        result = text.replace(Regex("[^0-9]"), "") // delete all non number chars
        return "$firstChar$result" // concatenate 1st char (when it is "-"
    }
}


//--------------------------------------
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview(model: DemoViewModel = viewModel()) {
    MyApplication21ViewmodelsTheme {
        MainScreen(
            isFahrenheit = model.isFahrenheit,
            result = model.result,
            convertTemp = { model.convertTemp(it) },
            switchChange = { model.switchChange() },
            removeSpecialChars = { model.removeSpecialChars(it)}
        )
    }
}