package com.example.myapplication19_animatestate

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.myapplication19_animatestate.ui.theme.MyApplication19AnimateStateTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication19AnimateStateTheme {
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

//----------------------------------------------------------
enum class BoxColor {
    Red, Magenta
}

enum class BoxPosition {
    Start, End
}

//----------------------------------------------------------
@Composable
fun MainScreen() {
    Column(Modifier.verticalScroll(rememberScrollState())) {
        RotationDemo()
        ColorChangeDemo()
        MotionDemo()
        TransitionDemo()
    }
}
// animateFloatAsState()
// -------------------------
@Composable
fun RotationDemo() {
//    var rotated by remember { mutableStateOf(false) }
    var angleValue by remember { mutableStateOf(0f) }

    val angle by animateFloatAsState(
//        targetValue = if(rotated) 360f else 0f,
        targetValue = angleValue,
        animationSpec = tween(durationMillis = 2500),
        label = "rotation"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.propeller),
            contentDescription = "fan",
            modifier = Modifier
                .rotate(angle) // here is the way to use the angle
                .padding(10.dp)
                .size(150.dp)
        )
        Button(
//            onClick = { rotated = !rotated },
            onClick = { angleValue += 360f },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Rotate Propeller")
        }
    }
}
// animateColorAsState()
// -------------------------
@Composable
fun ColorChangeDemo() {
    var colorState by remember { mutableStateOf(BoxColor.Red) }

    val animateColor: Color by animateColorAsState(
        targetValue = when (colorState) {
            BoxColor.Red -> Color.Red
            BoxColor.Magenta -> Color.Magenta
        },
        animationSpec = tween(4500),
        label = "color changing"
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .size(150.dp)
                .background(animateColor)
        )
        Button(
            onClick = {
                colorState = when (colorState) {
                    BoxColor.Red -> BoxColor.Magenta
                    BoxColor.Magenta -> BoxColor.Red
                }
            },
            modifier = Modifier.padding(10.dp)
        ) {
            Text(text = "Change color")
        }
    }
}

// animateDpAsState()
// -------------------------
@Composable
fun MotionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    val boxSideLength = 70.dp
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    val animatedOffset: Dp by animateDpAsState(
        targetValue = when (boxState) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - boxSideLength
        },
//        animationSpec = spring(
//            dampingRatio = DampingRatioHighBouncy, // const setup to 0.2f
//            stiffness = StiffnessVeryLow, // const setup to 50f
//        ),
        animationSpec = keyframes {
            durationMillis = 1000
            100.dp.at(10).with(LinearEasing)
            110.dp.at(500).with(FastOutSlowInEasing)
            200.dp.at(700).with(LinearOutSlowInEasing)
        },
        label = "object moving"
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(boxSideLength)
                .background(Color.DarkGray)
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move Box")
        }
    }
}
// updateTransition()
// -------------------------
@Composable
fun TransitionDemo() {
    var boxState by remember { mutableStateOf(BoxPosition.Start) }
    var screenWidth = LocalConfiguration.current.screenWidthDp.dp
    val transition = updateTransition(
        targetState = boxState,
        label = "Color and Motion"
    )
    val animatedColor: Color by transition.animateColor(
        transitionSpec = {
            tween(4000)
        },
        label = "colorAnimation"
    ) { state ->
        when (state) {
            BoxPosition.Start -> Color.Red
            BoxPosition.End -> Color.Magenta
        }
    }
    val animatedOffset: Dp by transition.animateDp(
        transitionSpec = {
            tween(4000)
        },
        label = "offesetAnimation"
    ) { state ->
        when (state) {
            BoxPosition.Start -> 0.dp
            BoxPosition.End -> screenWidth - 70.dp
        }
    }

    Column(Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .offset(x = animatedOffset, y = 20.dp)
                .size(70.dp)
                .background(animatedColor)
        )
        Spacer(Modifier.height(50.dp))
        Button(
            onClick = {
                boxState = when (boxState) {
                    BoxPosition.Start -> BoxPosition.End
                    BoxPosition.End -> BoxPosition.Start
                }
            },
            modifier = Modifier
                .padding(20.dp)
                .align(Alignment.CenterHorizontally)
        ) {
            Text(text = "Move + change color")
        }
    }
}

//----------------------------------------------------------
@Preview(showBackground = true)
@Composable
fun RotationDemoPrevied() {
    MyApplication19AnimateStateTheme {
        MainScreen()
    }
}
