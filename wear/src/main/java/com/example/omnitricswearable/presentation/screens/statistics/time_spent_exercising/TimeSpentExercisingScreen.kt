package com.example.omnitricswearable.presentation.screens.statistics.time_spent_exercising

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*

@Composable
fun TimeSpentExercisingScreen(
    viewModel: TimeSpentExercisingViewModel = hiltViewModel()
) {
    val timeSpentExercisingState by viewModel.timeSpentExercisingState.collectAsState()
    val timeSpentExercisingPerDay = timeSpentExercisingState.timeSpentExercising

    val listState = rememberScalingLazyListState()

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.scrollAway(listState))
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = listState
            )
        }
    ) {
        timeSpentExercisingPerDay?.let { timeSpentExercisingPerDay ->
//            val timeSpentExercisingList = listOf(
//                timeSpentExercisingPerDay.monday,
//                timeSpentExercisingPerDay.tuesday,
//                timeSpentExercisingPerDay.wednesday,
//                timeSpentExercisingPerDay.thursday,
//                timeSpentExercisingPerDay.friday,
//                timeSpentExercisingPerDay.saturday,
//                timeSpentExercisingPerDay.sunday,
//            )

            val timeSpentExercisingList = listOf(1.0f, 2.0f, 3.0f, 4.1f, 1.0f, 0.5f, 1.5f)
            val dayListInitialLetter = listOf("L", "M", "X", "J", "V", "S", "D")
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = listState
            ) {
                item {
                    VerticalChart(
                        initialValues = timeSpentExercisingList,
                        xAxisScale = dayListInitialLetter
                    )
                }
            }
        }

        if (timeSpentExercisingState.error.isNotBlank()) {
            Text(
                text = timeSpentExercisingState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        if (timeSpentExercisingState.isLoading) {
            CircularProgressIndicator()
        }
    }

}