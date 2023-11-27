package com.example.omnitricswearable.presentation.screens.routine_details

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Assistant
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.Day
import com.example.omnitricswearable.domain.model.entities.RoutineDetails
import com.example.omnitricswearable.presentation.screens.routine_list.TextSeparator

@Composable
fun RoutineDetailsScreen(
    onNavigateExerciseList: () -> Unit,
    viewModel: RoutineDetailsViewModel = hiltViewModel()
) {
    val routineState by viewModel.routineState.collectAsState()
    val routine = routineState.routine

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
        routine?.let { routine ->
            ScalingLazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                state = lazyListState
            ) {
                item { TextSeparator("Detalles de la Rutina") }
                item { TextSeparator(routine.name) }
                item { FavouriteChip(viewModel, routine) }
                item { TextSeparator("Elegir Dia") }
                items(routine.dayList) { day ->
                    DayChip(day, onNavigateExerciseList, viewModel)
                }
            }
        }

        if (routineState.error.isNotBlank()) {
            Text(
                text = routineState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        if (routineState.isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun FavouriteChip(viewModel: RoutineDetailsViewModel, routine: RoutineDetails) {
    val checkedState by viewModel.favouriteToggle.collectAsState()

    ToggleChip(
        label = {
            Text("Favorito", maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        checked = checkedState,
        colors = ToggleChipDefaults.toggleChipColors(
            uncheckedToggleControlColor = ToggleChipDefaults.SwitchUncheckedIconColor
        ),
        toggleControl = {
            Switch(
                checked = checkedState,
                enabled = true,
                modifier = Modifier.semantics {
                    this.contentDescription =
                        if (checkedState) "On" else "Off"
                }
            )
        },
        onCheckedChange = {
            viewModel.toggleFavourite(routine.id, it)
        },
        appIcon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "Favourite Routine",
                modifier = Modifier
                    .size(24.dp)
                    .wrapContentSize(align = Alignment.Center),
            )
        },
        enabled = true,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}


@Composable
fun DayChip(day: Day, onClick: () -> Unit, viewModel: RoutineDetailsViewModel) {
    Chip(
        label = {
            Text(
                text = "Dia ${day.dayPosition}",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryLabel = {
            Text(
                text = day.description,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.Assistant,
                contentDescription = "Go to exercise list"
            )
        },
        onClick = {
            viewModel.onSelectedDayChange(day)
            onClick()
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}