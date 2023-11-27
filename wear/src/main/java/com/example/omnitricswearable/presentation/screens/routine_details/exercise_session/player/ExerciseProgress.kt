package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.wear.compose.material.*
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.DisplayProgressStatus

@Composable
fun ExerciseProgress(
    viewModel: RoutineDetailsViewModel,
    onNavigateSetStatisticsProgress: (Int) -> Unit,
    exerciseProgressComponentlazyListState: ScalingLazyListState
) {
    val day by viewModel.selectedDay.collectAsState()

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = exerciseProgressComponentlazyListState
    ) {

        itemsIndexed(day.exerciseList) { index, exercise ->
            val exerciseName = exercise.exerciseInfo.name
            val currentEmoji = getProgressEmoji(index, viewModel)

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SplitToggleChip(
                    label = {
                        Text(
                            "$currentEmoji  $exerciseName",
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    checked = true,
                    toggleControl = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "See statistics progress for $exerciseName"
                        )
                    },
                    onCheckedChange = {
                        onNavigateSetStatisticsProgress(index)
                    },
                    onClick = { viewModel.selectANonFinishedExercise(index) },
                    enabled = true,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun getProgressEmoji(index: Int, viewModel: RoutineDetailsViewModel): String {
    val displayVisualProgress by viewModel.displayVisualProgress.collectAsState()
    val exerciseStatus = displayVisualProgress[index]

    return when (exerciseStatus) {
        DisplayProgressStatus.SELECTED -> "⬤"
        DisplayProgressStatus.FINISHED -> "✔"
        DisplayProgressStatus.STARTED -> "⛛"
        DisplayProgressStatus.NOT_STARTED -> "✖"
    }
}