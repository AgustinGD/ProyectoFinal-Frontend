package com.example.omnitricswearable.presentation.screens.routine_details.exercise_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Assistant
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.ExerciseInfo
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.screens.routine_list.TextSeparator

@Composable
fun ExerciseListScreen(
    onNavigateExercisePlayer: () -> Unit,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {
    val day by viewModel.selectedDay.collectAsState()

    val lazyListState = rememberScalingLazyListState()

    Scaffold(
        timeText = {
            TimeText(modifier = Modifier.scrollAway(lazyListState))
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = lazyListState
            )
        }
    ) {
        ScalingLazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            state = lazyListState
        ) {
            item { TextSeparator("Lista de Ejercicios") }
            item { PlayerChip(onClick = onNavigateExercisePlayer) }
            item { TextSeparator("Dia ${day.dayPosition}") }
            items(day.exerciseList) { exercise ->
                ExerciseChip(exercise.exerciseInfo)
            }
        }
    }
}

@Composable
fun ExerciseChip(exercise: ExerciseInfo) {
    Chip(
        label = {
            Text(
                text = exercise.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Assistant,
                contentDescription = "Exercise Information"
            )
        },
        onClick = { },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}

@Composable
fun PlayerChip(onClick: () -> Unit) {
    Chip(
        label = {
            Text(
                text = "Empezar Sesion",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.PlayArrow,
                contentDescription = "Go to Exercise Player"
            )
        },
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}