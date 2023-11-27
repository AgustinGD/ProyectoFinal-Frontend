package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.statistic_pickers

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel

@Composable
fun RepetitionsPickerScreen(
    viewModel: RoutineDetailsViewModel = hiltViewModel(),
    onGoBack: () -> Boolean
){
    val titleSizeStyle = TextStyle(fontSize = 16.sp)
    val pickerSizeModifier = Modifier.size(60.dp, 60.dp)

    RepetitionsPicker(viewModel = viewModel, titleSizeStyle = titleSizeStyle, modifier = pickerSizeModifier, onGoBack = onGoBack)
}

@Composable
private fun RepetitionsPicker(
    viewModel: RoutineDetailsViewModel,
    titleSizeStyle: TextStyle,
    modifier: Modifier,
    onGoBack: () -> Boolean
) {
    val repetitionsOption by viewModel.selectedRepetitions.collectAsState()

    val repetitionState = rememberPickerState(
        initialNumberOfOptions = 100,
        initiallySelectedOption = repetitionsOption
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier,
            text = "Repeticiones",
            style = titleSizeStyle
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Picker(
                modifier = modifier,
                state = repetitionState,
                contentDescription = "Integer Repetition Picker",
            ) { repetition: Int ->
                val hourString = "%02d".format(repetition)

                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = hourString,
                        maxLines = 1,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .wrapContentSize()
                    )
                }
            }
        }
        AcceptButton(viewModel, repetitionState, onGoBack)
    }
}

@Composable
fun AcceptButton(
    viewModel: RoutineDetailsViewModel,
    repetitionsState: PickerState,
    onGoBack: () -> Boolean
) {
    Button(
        onClick = {
            viewModel.onSelectedRepetitions(repetitionsState.selectedOption)

            onGoBack()
        }
    ) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = "Accept changes",
            modifier = Modifier
                .size(24.dp)
                .wrapContentSize(align = Alignment.Center)
        )
    }
}