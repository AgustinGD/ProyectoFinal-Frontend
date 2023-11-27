package com.example.omnitricswearable.presentation.screens.statistics.time_spent_exercising

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.Text
import com.example.omnitricswearable.presentation.theme.wearColorPalette
import kotlinx.coroutines.delay
import kotlin.math.ceil

@Composable
fun VerticalChart(
    initialValues: List<Float>,
    xAxisScale: List<String>
) {
    val context = LocalContext.current

    // BarGraph Dimensions
    val barGraphHeight by remember { mutableStateOf(100.dp) }
    val barGraphWidth by remember { mutableStateOf(16.dp) }
    val spaceBetweenBars by remember { mutableStateOf(5.dp) }

    // Scale Dimensions
    val scaleYAxisWidth by remember { mutableStateOf(30.dp) }
    val scaleYAxisPadding by remember { mutableStateOf(5.dp) }
    val scaleLineWidth by remember { mutableStateOf(1.dp) }

    // Bar values formatting
    val initialValuesRounded = initialValues.map { String.format("%.2f", it).toFloat() }
    // Minimum max time is 1 hr in case of an untrained week.
    var maxTime = 1f
    val initialValuesMaxTime = initialValuesRounded.maxOf { it }
    val initialValuesMaxCeil = ceil(initialValuesMaxTime)
    maxTime = maxTime.coerceAtLeast(initialValuesMaxCeil)

    val barValuesRounded = initialValuesRounded.map { it / maxTime }

    // YAxis formatting
    val timeUnit = "hr"
    val halfTime = maxTime / 2
    val maxTimeRowFormat = String.format("%d", maxTime.toInt())
    val halfTimeRowFormat = String.format("%.1f", halfTime)

    val maxTimeRow = "$maxTimeRowFormat $timeUnit"
    val halfTimeRow = "$halfTimeRowFormat $timeUnit"

    var startHeightAnimation by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .padding(0.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(barGraphHeight),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start
        ) {
            // scale Y-Axis
            Box(
                modifier = Modifier
                    .padding(start = scaleYAxisPadding)
                    .fillMaxHeight()
                    .width(scaleYAxisWidth),
                contentAlignment = Alignment.BottomCenter
            ) {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = maxTimeRow,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.fillMaxHeight())
                }

                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Text(
                        text = halfTimeRow,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.fillMaxHeight(0.5f))
                }
            }

            // Y-Axis Line
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(scaleLineWidth)
                    .background(Color.White)
            )

            // Start Bar Height Animation after a delay
            LaunchedEffect(startHeightAnimation) {
                delay(300)
                startHeightAnimation = true
            }

            // graph
            barValuesRounded.forEach {

                val animatedHeight by animateFloatAsState(
                    targetValue = if (startHeightAnimation) it else 0f,
                    animationSpec = tween(
                        durationMillis = 1000
                    ),
                    visibilityThreshold = 0.1f,
                    label = "Bar Height Animation"
                )
                
                Box(
                    modifier = Modifier
                        .padding(start = spaceBetweenBars, bottom = 5.dp)
                        .clip(CircleShape)
                        .width(barGraphWidth)
                        .fillMaxHeight(animatedHeight)
                        .background(wearColorPalette.primary)
                        .clickable {
                            Toast
                                .makeText(
                                    context,
                                    toastFormat(it, initialValuesMaxTime),
                                    Toast.LENGTH_SHORT
                                )
                                .show()
                        }
                )
            }
        }


        // X-Axis Line
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(scaleLineWidth)
                .background(Color.White)
        )

        // Scale X-Axis
        Row(
            modifier = Modifier
                .padding(start = scaleYAxisPadding + scaleYAxisWidth + spaceBetweenBars + (barGraphWidth / 2 - 2.dp) + scaleLineWidth)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(barGraphWidth)
        ) {

            xAxisScale.forEach {
                Text(
                    modifier = Modifier.width(spaceBetweenBars),
                    text = it,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

private fun toastFormat(fl: Float, maxTimeRounded: Float): String {
    val realValue = fl * maxTimeRounded
    val realValueOneDecimal = String.format("%.1f", realValue)
    return "$realValueOneDecimal hr"
}