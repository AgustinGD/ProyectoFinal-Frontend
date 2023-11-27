package com.example.omnitricswearable.presentation.screens.statistics

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*

@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel(),
    onNavigateTimeSpentExercisingScreen: () -> Unit
) {
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
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = listState
        ) {
            item {
                Text(
                    text = "Estadisticas",
                    fontSize = MaterialTheme.typography.title3.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
            item { TimeSpentScreenChip(onNavigateTimeSpentExercisingScreen) }
        }
    }

}

@Composable
fun TimeSpentScreenChip(onClick: () -> Unit) {
    Chip(
        label = {
            Text(
                text = "Tiempo Ejercitado",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Leaderboard,
                contentDescription = "Go to time spent exercising screen"
            )
        },
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}