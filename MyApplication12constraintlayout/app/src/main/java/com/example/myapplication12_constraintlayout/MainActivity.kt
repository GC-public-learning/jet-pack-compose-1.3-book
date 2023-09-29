package com.example.myapplication12_constraintlayout

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.example.myapplication12_constraintlayout.ui.theme.MyApplication12ConstraintLayoutTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplication12ConstraintLayoutTheme {
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
    Column(Modifier.fillMaxWidth()) {
        Row {
            // ZONE 1 (gray): MARGIN & OPPOSING CONSTRAINTS
            // ----------------------------------------------------------------------
            Box(Modifier
                .fillMaxWidth(0.5f)
                .height(200.dp)
            ) {
                ConstraintLayout(
                    Modifier
                        .fillMaxSize()
                        .background(Color.Gray)
                ) {
                    val (button1, button2, button3) = createRefs()
                    // margin constraints & Opposing
                    MyButton(
                        text = "Button 1",
                        Modifier.constrainAs(button1) {
                            top.linkTo(parent.top, margin = 20.dp)
                            start.linkTo(parent.start, margin = 30.dp)
                            end.linkTo(parent.end)

                        })
                    // center without margin
                    MyButton(
                        text = "Button 2",
                        Modifier.constrainAs(button2) {
                            centerHorizontallyTo(parent)
                            centerVerticallyTo(parent)
                        })
                    MyButton(
                        text = "Button 3",
                        Modifier.constrainAs(button3) {
                            linkTo(parent.top, parent.bottom, bias = 0.9f)
                        }
                    )

                }
            }
            // ZONE 4 (yellow) CONSTRAINT SETS
            // ----------------------------------------------------------------------
            Box(Modifier
                .fillMaxWidth()
                .height(200.dp)
            ) {
                // ! createRefs() cannot be used
                val constraints = myConstraintSet(margin = 30.dp) // custom constraints
                ConstraintLayout(
                    constraints,
                    Modifier
                        .fillMaxSize()
                        .background(Color.Yellow)
                ) {
                    MyButton(text = "Button 4", Modifier.layoutId("button4"))
                }


            }
        }
        // ZONE 2 (cyan) : CHAIN & GUIDELINE
        // ----------------------------------------------------------------------
        Box(Modifier
            .fillMaxWidth(0.5f)
            .height(200.dp)
        ) {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .background(Color.Cyan)
            ){
                val (button1, button2, button3) = createRefs()
                createVerticalChain(
                    button1, button2, button3,
//                    chainStyle = ChainStyle.SpreadInside
                    chainStyle = ChainStyle.Packed
                ) // chain
//                val guide = createGuidelineFromTop(offset = 60.dp) // horizontal guideline
                val guide = createGuidelineFromStart(fraction = .10f) // vertical guideline
                MyButton(
                    text = "Button 1",
                    Modifier.constrainAs(button1) {
//                        centerHorizontallyTo(parent)
                        start.linkTo(guide)
                    }
                )
                MyButton(
                    text = "Button 2",
                    Modifier.constrainAs(button2) {
//                        centerHorizontallyTo(parent)
                        start.linkTo(guide, margin = 20.dp)
                    }
                )
                MyButton( // all items for the chain have to be implemented to make the chain working
                    text = "Button 3",
                    Modifier.constrainAs(button3) {
//                        centerHorizontallyTo(parent)
                        start.linkTo(guide, margin = 40.dp)
                    }
                )
            }
        }
        // ZONE 3 (red) : BARRIER & DIMENSIONS
        // ----------------------------------------------------------------------
        Box(Modifier.fillMaxSize()) {
            ConstraintLayout(
                Modifier
                    .fillMaxSize()
                    .background(Color.Red)
            ){
                val (button1, button2, button3) = createRefs()
                val barrier = createEndBarrier(button1, button2) // vertical barrier

                MyButton(
                    text = "Button 1",
                    Modifier
                        .constrainAs(button1) {
                        start.linkTo(parent.start)
                        top.linkTo(parent.top, margin = 10.dp)
                    }
                )
                MyButton(
                    text = "Button 2",
                    Modifier
                        .width(150.dp)
                        .constrainAs(button2) {
                            start.linkTo(parent.start)
                            top.linkTo(button1.bottom, margin = 10.dp)
                        }
                )
                MyButton( // all items for the chain have to be implemented to make the chain working
                    text = "Button 3",
                    Modifier.constrainAs(button3) {
                        linkTo(
                            parent.top, parent.bottom,
                            topMargin = 10.dp, bottomMargin = 10.dp
                        )
                        linkTo(
                            button1.end, parent.end,
                            startMargin = 10.dp, endMargin = 10.dp
                        )
                        start.linkTo(barrier, margin = 10.dp) // use of barrier
                        width = Dimension.fillToConstraints
                        height = Dimension.fillToConstraints
                    }
                )
            }
        }

    }
}
// for zone 4 (yellow) : constraint sets
private fun myConstraintSet(margin: Dp) : ConstraintSet
    = ConstraintSet {
        val button4 = createRefFor("button4")

        constrain(button4) {
            linkTo(
                parent.top, parent.bottom,
                topMargin = margin, bottomMargin = margin
            )
            linkTo(
                parent.start, parent.end,
                startMargin = margin, endMargin = margin
            )
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
}
// ---------------------------------------------------

// for all zones
@Composable
fun MyButton(text: String, modifier: Modifier = Modifier) {
    Button(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Text(text = text)
    }
}
// ---------------------------------------------------
@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    MainScreen()
}
