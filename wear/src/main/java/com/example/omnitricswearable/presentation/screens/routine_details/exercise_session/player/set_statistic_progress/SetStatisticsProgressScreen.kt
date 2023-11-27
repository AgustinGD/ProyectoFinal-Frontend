package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.set_statistic_progress

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel

@Composable
fun SetStatisticProgressScreen(
    viewModel: RoutineDetailsViewModel = hiltViewModel(),
    exerciseIndex: Int?
) {

    val lazyListState = rememberScalingLazyListState()
    val timer by viewModel.stopWatchState.collectAsState()

    //Confio en que se envia por la ruta un Integer del index
    val exerciseStatPair = viewModel.getSetStatisticsForExercise(exerciseIndex!!)
    val currentExercise = exerciseStatPair.first
    val currentExerciseSetProgress = exerciseStatPair.second

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = lazyListState
    ) {
        item {
            Text(text = currentExercise.exerciseInfo.name)
        }
        items(currentExerciseSetProgress) { completedSet ->

            val durationTimeInHrs = timer.formatTime(completedSet.durationTimeInMillis)
            val restTimeInHrs = timer.formatTime(completedSet.restTimeInMillis)

            Card(
                onClick = {}
            ) {
                Column {
                    Text(
                        text = "Serie ${completedSet.setNumber + 1}",
                        style = TextStyle(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Repeticiones: ${completedSet.repetitions}")
                    Text("Peso: ${completedSet.weightInGrams}g")
                    Text("Ritmo Cardiaco: ${completedSet.heartRate}")
                    Text("Entrenado: $durationTimeInHrs")
                    Text("Descansado: $restTimeInHrs")
                }
            }
        }
        item {
            if (currentExerciseSetProgress.isEmpty()) {
                Text(
                    text = "(No hay Series completadas)",
                    style = TextStyle(color = MaterialTheme.colors.secondary)
                )
            }
        }
    }
}