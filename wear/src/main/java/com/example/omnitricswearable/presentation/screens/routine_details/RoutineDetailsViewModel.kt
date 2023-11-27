package com.example.omnitricswearable.presentation.screens.routine_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omnitricswearable.common.Constants
import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedSetDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.Day
import com.example.omnitricswearable.data.remote.omnitrics.dto.favourite.FavouriteDto
import com.example.omnitricswearable.data.sensor.MeasurableSensor
import com.example.omnitricswearable.domain.model.stopwatch.StopWatch
import com.example.omnitricswearable.domain.use_case.favourite_a_routine.PatchFavouriteARoutineUseCase
import com.example.omnitricswearable.domain.use_case.get_routine.GetRoutineUseCase
import com.example.omnitricswearable.domain.use_case.patch_completed_sets.PatchCompletedSetsUseCase
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.ExerciseProgressManager
import com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils.WorkoutStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@HiltViewModel
class RoutineDetailsViewModel @Inject constructor(
    private val getRoutineUseCase: GetRoutineUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val heartRateSensor: MeasurableSensor,
    private val patchFavouriteARoutineUseCase: PatchFavouriteARoutineUseCase,
    private val patchCompletedSetsUseCase: PatchCompletedSetsUseCase,
    private val exerciseProgressManager: ExerciseProgressManager
) : ViewModel() {
    //For RoutineDetailsScreen
    private val _routineState = MutableStateFlow(RoutineDetailsState())
    val routineState = _routineState.asStateFlow()

    private val _favouriteToggle = MutableStateFlow(false)
    val favouriteToggle = _favouriteToggle.asStateFlow()

    private val _selectedDay = MutableStateFlow(Day("", "", -1, emptyList()))
    val selectedDay = _selectedDay.asStateFlow()

    //For ExercisePlayerScreen
    private val _workoutStatus = MutableStateFlow(WorkoutStatus.STOPPED)
    val workoutStatus = _workoutStatus.asStateFlow()

    private val _heartRate = MutableStateFlow(0F)
    val heartRate = _heartRate.asStateFlow()

    private var currentTimeSpentInExercise = 0L
    private val currentTimeSpentInRest = MutableStateFlow(0L)

    //For Set Log Picker Composable
    private val _moreWeightMode = MutableStateFlow(false)
    val moreWeightMode = _moreWeightMode.asStateFlow()

    private val _integerWeightQuantityOptions = MutableStateFlow(500)
    val integerWeightQuantityOptions = _integerWeightQuantityOptions.asStateFlow()

    private val _decimalWeightQuantityOptions = MutableStateFlow(7)
    val decimalWeightQuantityOptions = _decimalWeightQuantityOptions.asStateFlow()

    private val _integerWeightOption = MutableStateFlow(0)
    val integerWeightOption = _integerWeightOption.asStateFlow()

    private val _decimalWeightOption = MutableStateFlow(0)
    val decimalWeightOption = _decimalWeightOption.asStateFlow()

    private var _integerWeightUnit = 0

    private val _selectedRepetitions = MutableStateFlow(0)
    val selectedRepetitions = _selectedRepetitions.asStateFlow()

    private val _selectedIntegerWeight = MutableStateFlow(0)
    val selectedIntegerWeight = _selectedIntegerWeight.asStateFlow()

    private val _selectedDecimalWeight = MutableStateFlow(0)
    val selectedDecimalWeight = _selectedDecimalWeight.asStateFlow()

    private val _stopWatchState = MutableStateFlow(StopWatch())
    val stopWatchState = _stopWatchState.asStateFlow()

    // For ExerciseProgress Composable
    val displayVisualProgress = exerciseProgressManager.displayVisualProgress
    val currentSetIndex = exerciseProgressManager.currentSetIndex

    init {
        // Obtiene el parametro pasado por navegacion
        savedStateHandle.get<String>(Constants.PARAM_ROUTINE_ID)?.let { routineId ->
            getRoutine(routineId)
        }

        // Inicializacion del sensor
        heartRateSensor.startListening()
        heartRateSensor.setOnSensorValuesChangedListener { values ->
            val heartRate = values[0]
            _heartRate.value = heartRate
        }
        println("Sensor Found: ${heartRateSensor.doesSensorExist}")
    }

    private fun getRoutine(id: String) {
        getRoutineUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _routineState.value = RoutineDetailsState(routine = result.data)
                    _favouriteToggle.value = result.data!!.isFavourite
                }
                is Resource.Error -> {
                    _routineState.value = RoutineDetailsState(
                        error = result.message ?: "Error inesperado"
                    )
                }
                is Resource.Loading -> {
                    _routineState.value = RoutineDetailsState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleFavourite(routineDetailsId: String, isfavourite: Boolean) {

        val favouriteDto = FavouriteDto(isFavourite = isfavourite)

        patchFavouriteARoutineUseCase(routineDetailsId, favouriteDto).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _favouriteToggle.value = result.data ?: !isfavourite
                }
                is Resource.Error -> {
                    //TODO("notificar")
                    _favouriteToggle.value = !isfavourite
                }
                is Resource.Loading -> {
                    _favouriteToggle.value = !isfavourite
                }
            }
        }.launchIn(viewModelScope)
    }

    fun toggleMoreWeightMode() {
        when (_moreWeightMode.value) {
            false -> {
                val lastDigit = _integerWeightOption.value % 10
                val newPosition = _integerWeightOption.value / 10
                val newOptions = _integerWeightQuantityOptions.value / 10

                _integerWeightUnit = lastDigit
                _integerWeightOption.value = newPosition
                _integerWeightQuantityOptions.value = newOptions
            }
            true -> {
                val lastDigit = 0
                val newPosition = _integerWeightOption.value * 10 + _integerWeightUnit
                val newOptions = _integerWeightQuantityOptions.value * 10

                _integerWeightUnit = lastDigit
                _integerWeightOption.value = newPosition
                _integerWeightQuantityOptions.value = newOptions
            }
        }

        _moreWeightMode.value = !_moreWeightMode.value
    }

    fun getCurrentIntegerWeight(decimalWeightOption: Int): Int {

        val multiplier = when (_moreWeightMode.value) {
            true -> 10
            false -> 1
        }

        return decimalWeightOption * multiplier + _integerWeightUnit
    }

    fun getCurrentDecimalWeight(integerWeightOption: Int): Int {
        return integerWeightOption * 125
    }

    fun onSelectedDayChange(newDay: Day) {
        _selectedDay.value = newDay
        exerciseProgressManager.changeExerciseList(newDay.exerciseList)
    }

    fun startPlaying() {
        storeTimeRested()
        startTimer()
        _workoutStatus.value = WorkoutStatus.PLAYING
    }

    private fun storeTimeRested() {
        val itWasNotPaused = _workoutStatus.value != WorkoutStatus.PAUSED
        if (itWasNotPaused) {
            pauseTimer()
            currentTimeSpentInRest.value = _stopWatchState.value.timeMillis.value
            stopTimer()
        }
    }

    fun pause() {
        pauseTimer()
        _workoutStatus.value = WorkoutStatus.PAUSED
    }

    fun stopPlaying() {
        _workoutStatus.value = WorkoutStatus.STOPPED

        storeTimeExercisedAndStopTimer()
        val isNotLastSet = exerciseProgressManager.isNotLastSet()
        saveCurrentSet()

        // Si no es el ultimo Set, empiezo el timer para contar descanso
        if (isNotLastSet)
            startTimer()

        if (exerciseProgressManager.finishedExercising())
            _workoutStatus.value = WorkoutStatus.FINISHED
    }

    private fun storeTimeExercisedAndStopTimer() {
        pauseTimer()
        currentTimeSpentInExercise = _stopWatchState.value.timeMillis.value
        stopTimer()
    }

    private fun saveCurrentSet() {
        val weightInGrams =
            _integerWeightOption.value * 1000 + _decimalWeightOption.value * 125

        val completedSet = CompletedSetDto(
            repetitions = _selectedRepetitions.value,
            setNumber = exerciseProgressManager.currentSetIndex.value,
            weightInGrams = weightInGrams.toLong(),
            heartRate = _heartRate.value,
            durationTimeInMillis = currentTimeSpentInExercise,
            restTimeInMillis = currentTimeSpentInRest.value
        )

        exerciseProgressManager.saveCurrentSetAndGoToNextSet(completedSet)
    }


    private fun stopTimer() {
        _stopWatchState.value.reset()
    }

    private fun startTimer() {
        _stopWatchState.value.start()
    }

    private fun pauseTimer() {
        _stopWatchState.value.pause()
    }

    fun upload() =
        patchCompletedSetsUseCase(exerciseProgressManager.toCompletedExerciseDto()).launchIn(
            viewModelScope
        )


    fun onSelectedIntegerWeight(newIntegerWeight: Int) {
        _integerWeightOption.value = newIntegerWeight
    }

    fun onSelectedDecimalWeight(newDecimalWeight: Int) {
        _decimalWeightOption.value = newDecimalWeight
    }

    fun onSelectedRepetitions(newRepetitions: Int) {
        _selectedRepetitions.value = newRepetitions
    }

    fun updateWeightValues() {
        _selectedIntegerWeight.value = getCurrentIntegerWeight(_integerWeightOption.value)
        _selectedDecimalWeight.value = getCurrentDecimalWeight(_decimalWeightOption.value)
    }

    fun selectANonFinishedExercise(exerciseIndex: Int) =
        exerciseProgressManager.selectANonFinishedExercise(exerciseIndex)

    fun getSetStatisticsForExercise(exerciseIndex: Int) =
        exerciseProgressManager.getSetStatisticsForExercise(exerciseIndex)

    fun getCurrentExercise() = exerciseProgressManager.getCurrentExercise()

}