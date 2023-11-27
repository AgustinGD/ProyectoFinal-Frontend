package com.example.omnitricswearable.presentation.screens.routine_list

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FlagCircle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.domain.model.entities.Routine
import java.text.SimpleDateFormat

@Composable
fun RoutineListScreen(
    onNavigateRoutineDetails: (routineID: String) -> Unit,
    viewModel: RoutineListViewModel = hiltViewModel()
) {
    viewModel.updateRoutineList()
    val resetUpdateRoutineList = { viewModel.resetUpdateRoutineList() }

    val routineListState = viewModel.routineListState.value
    val routineList = routineListState.routines
    val sortedRoutineList = routineList.sortedWith(compareBy { it.createdAt })
    val favouriteRoutineList = sortedRoutineList.filter { it.isFavourite }

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
            item { ActiveRoutineText() }
            item { TextSeparator("Favoritos") }
            if (favouriteRoutineList.isEmpty())
                item {
                    Text(
                        text = "(No hay Rutinas Favoritas)",
                        style = TextStyle(color = MaterialTheme.colors.secondary)
                    )
                }
            items(favouriteRoutineList) { favouriteRoutine ->
                RoutineChip(favouriteRoutine, onNavigateRoutineDetails, resetUpdateRoutineList)
            }
            item { TextSeparator("Recientemente Usadas") }
            items(sortedRoutineList) { routine ->
                RoutineChip(routine, onNavigateRoutineDetails, resetUpdateRoutineList)
            }
            if (sortedRoutineList.isEmpty())
                item {
                    Text(
                        text = "(No hay Rutinas)",
                        style = TextStyle(color = MaterialTheme.colors.secondary)
                    )
                }
        }


        if (routineListState.error.isNotBlank()) {
            Text(
                text = routineListState.error,
                color = MaterialTheme.colors.error,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            )
        }

        if (routineListState.isLoading) {
            CircularProgressIndicator()
        }
    }
}


@Composable
fun ActiveRoutineText() {
    Text(
        text = "Lista de Rutinas",
        fontSize = MaterialTheme.typography.title3.fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun TextSeparator(title: String) {
    Text(
        text = title,
        fontSize = MaterialTheme.typography.title3.fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RoutineChip(
    routine: Routine,
    onClick: (routineID: String) -> Unit,
    resetUpdateRoutineList: () -> Unit
) {
    val format = SimpleDateFormat("dd/MM/yyyy")
    val formattedDate = format.format(routine.createdAt)

    Chip(
        label = {
            Text(
                text = routine.name,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        secondaryLabel = {
            Text(
                text = formattedDate,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.FlagCircle,
                contentDescription = "Go to routines list"
            )
        },
        onClick = {
            resetUpdateRoutineList()
            onClick(routine.id)
        },
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}