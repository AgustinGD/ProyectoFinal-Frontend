package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import androidx.wear.compose.material.dialog.Alert
import androidx.wear.compose.material.dialog.Dialog
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.WorkoutStatus
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.WorkoutStatusColor
import com.example.omnitricswearable.presentation.theme.wearColorPalette


@Composable
fun ExercisePlayerScreen(
    viewModel: RoutineDetailsViewModel = hiltViewModel(),
    onNavigateHome: (Int) -> Unit,
    onNavigateSetStatisticsProgress: (Int) -> Unit,
    onNavigateWeightPicker: () -> Unit,
    onNavigateRepetitionPicker: () -> Unit
) {
    val maxPages = 3
    var selectedPage by remember { mutableStateOf(1) }
    var finalValue by remember { mutableStateOf(0) }

    val heartRate by viewModel.heartRate.collectAsState()
    val workoutStatus by viewModel.workoutStatus.collectAsState()

    val animatedSelectedPage by animateFloatAsState(
        targetValue = selectedPage.toFloat(),
    ) {
        finalValue = it.toInt()
    }

    val pageIndicatorState: PageIndicatorState = remember {
        object : PageIndicatorState {
            override val pageOffset: Float
                get() = animatedSelectedPage - finalValue

            override val selectedPage: Int
                get() = finalValue

            override val pageCount: Int
                get() = maxPages
        }
    }

    val workoutStateText = when (workoutStatus) {
        WorkoutStatus.STOPPED -> "Descansando"
        WorkoutStatus.PLAYING -> "Entrenando"
        WorkoutStatus.PAUSED -> "Pausado"
        WorkoutStatus.FINISHED -> "Completado"
    }

    val exerciseProgressComponentlazyListState = rememberScalingLazyListState()
    val setLogPickersComponentlazyListState = rememberScalingLazyListState()
    val noPositionLazyListState = rememberScalingLazyListState()

    val positionIndicator = when (selectedPage) {
        0 -> exerciseProgressComponentlazyListState
        2 -> setLogPickersComponentlazyListState
        else -> noPositionLazyListState
    }

    Scaffold(
        timeText = {
            TimeText(
                modifier = Modifier,
                startCurvedContent = {
                    curvedText(
                        text = workoutStateText,
                        color = WorkoutStatusColor.getColor(workoutStatus)
                    )
                },
                endCurvedContent = {
                    curvedText(
                        text = "$heartRate bpm",
                        color = wearColorPalette.secondary
                    )
                }
            )
        },
        vignette = {
            Vignette(vignettePosition = VignettePosition.TopAndBottom)
        },
        positionIndicator = {
            PositionIndicator(
                scalingLazyListState = positionIndicator
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(6.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.size(200.dp, 100.dp)
                ) {
                    when (selectedPage) {
                        0 -> ExerciseProgress(
                            viewModel,
                            onNavigateSetStatisticsProgress,
                            exerciseProgressComponentlazyListState
                        )
                        1 -> ExerciseInfo(viewModel, workoutStatus)
                        2 -> SetLogPickers(
                            viewModel,
                            onNavigateWeightPicker,
                            onNavigateRepetitionPicker,
                            setLogPickersComponentlazyListState
                        )
                    }
                }
                ActionBar(
                    viewModel = viewModel,
                    workoutStatus = workoutStatus,
                    previousPage = { selectedPage = (selectedPage - 1 + maxPages) % maxPages },
                    nextPage = { selectedPage = (selectedPage + 1) % maxPages },
                    selectedPage = selectedPage,
                    onNavigateHome = onNavigateHome
                )
            }
            HorizontalPageIndicator(
                pageIndicatorState = pageIndicatorState
            )
        }
    }
}

@Composable
private fun RightButton(nextPage: () -> Unit) {
    Button(
        onClick = nextPage,
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowRight,
            contentDescription = "Go to next page",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
private fun LeftButton(previousPage: () -> Unit) {
    Button(
        onClick = previousPage,
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowLeft,
            contentDescription = "Go to previous page",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
fun ActionBar(
    viewModel: RoutineDetailsViewModel,
    previousPage: () -> Unit,
    nextPage: () -> Unit,
    selectedPage: Int,
    workoutStatus: WorkoutStatus,
    onNavigateHome: (Int) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LeftButton(previousPage)
        when (workoutStatus) {
            WorkoutStatus.FINISHED -> FinishButton(viewModel, onNavigateHome)

            else -> when (selectedPage) {
                1 -> when (workoutStatus) {
                    WorkoutStatus.PLAYING -> {
                        PauseButton(viewModel)
                        StopButton(viewModel)
                    }

                    WorkoutStatus.STOPPED, WorkoutStatus.PAUSED -> {
                        PlayButton(viewModel)
                    }
                }
            }
        }
        RightButton(nextPage)
    }
}

@Composable
private fun PlayButton(viewModel: RoutineDetailsViewModel) {
    Button(
        onClick = { viewModel.startPlaying() },
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.PlayCircle,
            contentDescription = "Play Set",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
private fun PauseButton(viewModel: RoutineDetailsViewModel) {
    Button(
        onClick = { viewModel.pause() },
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.PauseCircle,
            contentDescription = "Pause Set",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
private fun StopButton(viewModel: RoutineDetailsViewModel) {
    Button(
        onClick = { viewModel.stopPlaying() },
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.StopCircle,
            contentDescription = "Stop Set",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
private fun FinishButton(viewModel: RoutineDetailsViewModel, onNavigateHome: (Int) -> Unit) {
    var uploadAlertShowDialog by remember { mutableStateOf(false) }

    Button(
        onClick = { uploadAlertShowDialog = true },
        modifier = Modifier.size(Constants.ACTIONBAR_BUTTON_FRAME_SIZE)
    ) {
        Icon(
            imageVector = Icons.Filled.Upload,
            contentDescription = "Play Set",
            modifier = Modifier
                .size(Constants.ACTIONBAR_BUTTON_ICON_SIZE)
                .wrapContentSize(align = Alignment.Center)
        )
    }

    Dialog(
        showDialog = uploadAlertShowDialog,
        onDismissRequest = {
            uploadAlertShowDialog = false
        }
    ) {
        Alert(
            title = {
                Text(
                    text = "¿Subir Resultados?",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colors.onBackground
                )
            },
            negativeButton = {
                Button(
                    onClick = {
                        uploadAlertShowDialog = false
                    },
                    colors = ButtonDefaults.secondaryButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Clear,
                        contentDescription = "No subir Resultados"
                    )
                }
            },
            positiveButton = {
                Button(
                    onClick = {
                        viewModel.upload()
                        uploadAlertShowDialog = false
                        onNavigateHome(1)
                    },
                    colors = ButtonDefaults.primaryButtonColors()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = "Subir Resultados"
                    )
                }
            }
        )
    }
}

@Composable
fun ExerciseInfo(viewModel: RoutineDetailsViewModel, workoutStatus: WorkoutStatus) {
    val selectedIntegerWeight by viewModel.selectedIntegerWeight.collectAsState()
    val selectedDecimalWeight by viewModel.selectedDecimalWeight.collectAsState()
    val selectedRepetitions by viewModel.selectedRepetitions.collectAsState()

    val currentSetIndex by viewModel.currentSetIndex.collectAsState()
    val currentSet = currentSetIndex + 1

    val stopWatch by viewModel.stopWatchState.collectAsState()
    val timeMillis by stopWatch.timeMillis.collectAsState()
    val formattedTime = stopWatch.formatTime(timeMillis)

    val currentExercise = viewModel.getCurrentExercise()


    val weight =
        if (selectedDecimalWeight != 0) "$selectedIntegerWeight,$selectedDecimalWeight" else "$selectedIntegerWeight"

    val maxLength = 17
    var exerciseName = currentExercise.exerciseInfo.name
    exerciseName = if (exerciseName.length > maxLength) "${
        exerciseName.substring(
            0,
            maxLength
        )
    }..." else exerciseName

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Serie $currentSet/${currentExercise.maxSets}",
            fontSize = MaterialTheme.typography.title3.fontSize,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Peso $weight Kg",
            fontSize = MaterialTheme.typography.title3.fontSize,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "$exerciseName X $selectedRepetitions",
            fontSize = MaterialTheme.typography.title3.fontSize,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.padding(6.dp))
        Text(
            text = formattedTime,
            fontSize = MaterialTheme.typography.title3.fontSize,
            fontWeight = FontWeight.Bold,
            color = WorkoutStatusColor.getColor(workoutStatus)
        )
    }
}