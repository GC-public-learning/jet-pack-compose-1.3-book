package com.example.myapplication18_visibilityanimation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkOut
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.myapplication18_visibilityanimation.ui.theme.MyApplication18VisibilityAnimationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication18VisibilityAnimationTheme {
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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun MainScreen() {
    var boxVisible by remember { mutableStateOf(true) }
//    val onClick = { newState: Boolean ->
//        boxVisible = newState
//    }

    val state = remember { MutableTransitionState(false) }
//    state.apply { targetState = true }
    val onClick2 = { newState: Boolean ->
        val state2 = state.apply { targetState = newState }
        // state2 is not used but if the line is not an assignation it is considered as return with lambda & it cannot
    }

    Column(
        Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Crossfade(
                targetState = state.currentState,
                animationSpec = tween(3000),
                label = ""
                ) { currenState ->
                when(currenState) {
                    true ->
//                        CustomButton(text = "Hide", targetState = false, onClick = onClick)
                        CustomButton(text = "Hide", targetState = false, onClick = onClick2)
                    false ->
//                        CustomButton(text = "Show", targetState = true, onClick = onClick)
                        CustomButton(text = "Show", targetState = true, onClick = onClick2)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        AnimatedVisibility(
            visibleState = state,
//            visible = boxVisible,
            enter = slideIn(
                initialOffset = { IntOffset(it.width, it.height)},
                animationSpec = tween(
                    durationMillis = 3000,
                    easing = LinearOutSlowInEasing) // effect become gradually slow
            ),
            exit = slideOut(
                targetOffset = { IntOffset(-it.width, it.height)},
                animationSpec = repeatable(
                    iterations = 5,
                    animation = tween(durationMillis = 1000),
                    repeatMode = RepeatMode.Reverse
                )
            )
        ){
            Row {
                Box(
                    modifier = Modifier
                        .size(height = 150.dp, width = 150.dp)
                        .background(Color.Blue)
                )
                Spacer(modifier = Modifier.width(20.dp))
                Box(
                    Modifier
                        .animateEnterExit(
                            enter = fadeIn(
                                animationSpec = tween(durationMillis = 3000)
                            ),
                            exit = fadeOut(
                                animationSpec = tween(durationMillis = 3000)
                            )
                        )
                        .size(width = 150.dp, height = 150.dp)
                        .background(Color.Red)
                )
            }
        }
    }
}

@Composable
fun CustomButton(
    text: String,
    targetState: Boolean,
    onClick: (Boolean) -> Unit,
    bgColor: Color = Color.Blue
) {
    Button(
        onClick = { onClick(targetState) },
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = Color.White
        )
    ) {
        Text(text = text)
    }
}

// ----------------------------

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MyApplication18VisibilityAnimationTheme {
        MainScreen()
    }
}