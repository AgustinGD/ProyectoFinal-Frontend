package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.*
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel
import com.example.omnitricswearable.presentation.theme.wearColorPalette

@Composable
fun SetLogPickers(
    viewModel: RoutineDetailsViewModel,
    onNavigateWeightPicker: () -> Unit,
    onNavigateRepetitionPicker: () -> Unit,
    setLogPickersComponentlazyListState: ScalingLazyListState
) {
    val integerWeight by viewModel.selectedIntegerWeight.collectAsState()
    val decimalWeight by viewModel.selectedDecimalWeight.collectAsState()

    val repetitions by viewModel.selectedRepetitions.collectAsState()

    val weight = "$integerWeight,$decimalWeight kg"
    val icon = Icons.Rounded.FitnessCenter

    ScalingLazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        state = setLogPickersComponentlazyListState
    ) {
        item {
            PickerChip(
                onClick = onNavigateWeightPicker,
                icon,
                "Peso",
                weight
            )
        }
        item {
            PickerChip(
                onClick = onNavigateRepetitionPicker,
                icon,
                "Repeticiones",
                repetitions.toString()
            )
        }
    }
}

@Composable
fun PickerChip(onClick: () -> Unit, icon: ImageVector, title: String, secondaryTitle: String) {
    val chipBorder = object : ChipBorder {
        @Composable
        override fun borderStroke(enabled: Boolean): State<BorderStroke?> {
            return remember {
                mutableStateOf(
                    BorderStroke(
                        width = 2.dp,
                        color = wearColorPalette.primary
                    )
                )
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedChip(
            label = {
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = wearColorPalette.onPrimary
                )
            },
            secondaryLabel = {
                Text(
                    text = secondaryTitle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = wearColorPalette.onPrimary
                )
            },
            icon = {
                Icon(
                    imageVector = icon,
                    contentDescription = "Go to Picker $title Screen",
                    tint = wearColorPalette.onPrimary
                )
            },
            border = chipBorder,
            onClick = onClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 14.dp)
        )
    }
}