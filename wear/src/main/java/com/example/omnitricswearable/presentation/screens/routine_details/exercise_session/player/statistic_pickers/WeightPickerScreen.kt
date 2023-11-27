package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.player.statistic_pickers

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.*
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsViewModel

@Composable
fun WeightPickerScreen(
    viewModel: RoutineDetailsViewModel = hiltViewModel(),
    onGoBack: () -> Boolean
) {
    val titleSizeStyle = TextStyle(fontSize = 16.sp)
    val pickerSizeModifier = Modifier.size(60.dp, 60.dp)

    WeightPicker(viewModel, titleSizeStyle, pickerSizeModifier, onGoBack)

}

@Composable
private fun WeightPicker(
    viewModel: RoutineDetailsViewModel,
    titleSizeStyle: TextStyle,
    modifier: Modifier,
    onGoBack: () -> Boolean
) {
    val integerWeightOption by viewModel.integerWeightOption.collectAsState()
    val integerWeightQuantityOptions by viewModel.integerWeightQuantityOptions.collectAsState()

    val decimalWeightQuantityOptions by viewModel.decimalWeightQuantityOptions.collectAsState()
    val decimalWeightOption by viewModel.decimalWeightOption.collectAsState()

    val integerWeightState = rememberPickerState(
        initialNumberOfOptions = integerWeightQuantityOptions,
        initiallySelectedOption = integerWeightOption
    )

    val decimalWeightState = rememberPickerState(
        initialNumberOfOptions = decimalWeightQuantityOptions,
        initiallySelectedOption = decimalWeightOption
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier,
                text = "Peso",
                style = titleSizeStyle
            )
            Spacer(Modifier.width(3.dp))
            MoreWeightButton(viewModel, integerWeightState)
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Picker(
                modifier = modifier,
                state = integerWeightState,
                contentDescription = "Integer Weight Picker"
            ) { integerWeight: Int ->
                val integerWeightString =
                    "%02d".format(viewModel.getCurrentIntegerWeight(integerWeight))

                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = integerWeightString,
                        maxLines = 1,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .wrapContentSize()
                    )
                }
            }
            Spacer(Modifier.width(1.dp))
            Text(
                text = ",",
                color = MaterialTheme.colors.onBackground
            )
            Spacer(Modifier.width(1.dp))
            Picker(
                modifier = modifier,
                state = decimalWeightState,
                contentDescription = "Decimal Weight Picker"
            ) { decimalWeight: Int ->
                val decimalWeightString =
                    "%02d".format(viewModel.getCurrentDecimalWeight(decimalWeight))

                Box(modifier = Modifier.fillMaxSize()) {
                    Text(
                        text = decimalWeightString,
                        maxLines = 1,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .wrapContentSize()
                    )
                }
            }
        }
        AcceptButton(viewModel, integerWeightState, decimalWeightState, onGoBack)
    }
}

@Composable
private fun MoreWeightButton(viewModel: RoutineDetailsViewModel, integerWeightState: PickerState) {
    val plusTen = "+10"
    val minusTen = "-10"

    var currentModeText by remember { mutableStateOf(plusTen) }
    val moreWeightMode by viewModel.moreWeightMode.collectAsState()

    Button(
        onClick = {
            currentModeText =
                if (moreWeightMode) plusTen else minusTen

            viewModel.onSelectedIntegerWeight(integerWeightState.selectedOption)
            viewModel.toggleMoreWeightMode()
        },
        modifier = Modifier.size(24.dp)
    ) {
        Text(
            text = currentModeText,
            style = TextStyle(
                fontSize = 10.sp,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .wrapContentSize(align = Alignment.Center)
        )
    }
}

@Composable
fun AcceptButton(
    viewModel: RoutineDetailsViewModel,
    integerWeightState: PickerState,
    decimalWeightState: PickerState,
    onGoBack: () -> Boolean
) {
    Button(
        onClick = {
            viewModel.onSelectedIntegerWeight(integerWeightState.selectedOption)
            viewModel.onSelectedDecimalWeight(decimalWeightState.selectedOption)
            viewModel.updateWeightValues()

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