package com.example.omnitricswearable.presentation.screens.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.CalendarMonth
import androidx.compose.material.icons.rounded.List
import androidx.compose.material.icons.rounded.PersonSearch
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import androidx.wear.compose.material.dialog.Confirmation
import androidx.wear.compose.material.dialog.Dialog

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onNavigateRoutineMenu: () -> Unit,
    onNavigateCalendarScreen: () -> Unit,
    onNavigateStatisticsScreen: () -> Unit
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
            item { HomeText() }
            item { RoutineChip(onNavigateRoutineMenu) }
            item { WorkoutSchedule(onNavigateCalendarScreen) }
            item { StatisticsChip(onNavigateStatisticsScreen) }
        }
    }
    UploadDialog(viewModel)
}

@Composable
fun HomeText() {
    Text(
        text = "Home",
        fontSize = MaterialTheme.typography.title3.fontSize,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun RoutineChip(onClick: () -> Unit) {
    Chip(
        label = {
            Text(
                text = "Lista de Rutinas",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.List,
                contentDescription = "Go to routines list"
            )
        },
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}

@Composable
fun WorkoutSchedule(onNavigateCalendarScreen: () -> Unit) {
    Chip(
        label = {
            Text(
                text = "Calendario",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.CalendarMonth,
                contentDescription = "Go to routines list"
            )
        },
        onClick = onNavigateCalendarScreen,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}

@Composable
fun StatisticsChip(onNavigateCalendarScreen: () -> Unit) {
    Chip(
        label = {
            Text(
                text = "Estadisticas",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        icon = {
            Icon(
                imageVector = Icons.Rounded.PersonSearch,
                contentDescription = "Go to Statistics"
            )
        },
        onClick = onNavigateCalendarScreen,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 14.dp)
    )
}

@Composable
fun UploadDialog(viewModel: HomeViewModel) {
    val confirmationShowDialog by viewModel.showDialog.collectAsState()

    Dialog(
        showDialog = confirmationShowDialog,
        onDismissRequest = { viewModel.onShowDialogChange(false) }
    ) {
        Confirmation(
            onTimeout = { viewModel.onShowDialogChange(false) },
            icon = {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "Uploaded Succesfully Icon",
                    modifier = Modifier.size(48.dp)
                )
            },
            durationMillis = 1500
        ) {
            Text(
                text = "¡Subido!",
                textAlign = TextAlign.Center
            )
        }
    }
}