package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils

import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedExerciseDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.completed.CompletedSetDto
import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.ExerciseStatistics
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ExerciseProgressManager @Inject constructor() {
    private var _exerciseList: List<ExerciseStatistics> = emptyList()
    private var _currentExerciseIndex = 0

    private var _currentSetIndex = MutableStateFlow(0)
    val currentSetIndex = _currentSetIndex.asStateFlow()

    private var _exerciseProgressSets: Array<Array<CompletedSetDto?>> = emptyArray()
    private var _currentExerciseProgress: Array<DisplayProgressStatus> = emptyArray()

    private var _displayVisualProgress = MutableStateFlow(emptyArray<DisplayProgressStatus>())
    val displayVisualProgress = _displayVisualProgress.asStateFlow()

    fun changeExerciseList(exerciseList: List<ExerciseStatistics>) {
        _exerciseList = exerciseList

        resetEverything()
    }

    private fun resetEverything() {
        // Initialize indexes
        _currentExerciseIndex = 0
        _currentSetIndex.value = 0

        // Initialize empty array of  Set progress
        val nestedArraySizes = _exerciseList.map { it.maxSets }.toIntArray()
        _exerciseProgressSets = Array(_exerciseList.size) { i -> arrayOfNulls(nestedArraySizes[i]) }

        // Initialize internal array of exercise progress
        _currentExerciseProgress = Array(_exerciseList.size) { DisplayProgressStatus.NOT_STARTED }

        // Initialize visual array of exercise progress
        _displayVisualProgress.value =
            Array(_exerciseList.size) { DisplayProgressStatus.NOT_STARTED }
        if (_currentExerciseIndex < _displayVisualProgress.value.size)
            _displayVisualProgress.value[_currentExerciseIndex] = DisplayProgressStatus.SELECTED
    }

    fun getSetStatisticsForExercise(exerciseIndex: Int): Pair<ExerciseStatistics, List<CompletedSetDto>> {
        val currentExercise = _exerciseList.elementAt(exerciseIndex)
        val currentExerciseSetProgress = _exerciseProgressSets[exerciseIndex].mapNotNull { it }

        return Pair(currentExercise, currentExerciseSetProgress)
    }

    fun getCurrentExercise(): ExerciseStatistics {
        return _exerciseList.elementAt(_currentExerciseIndex)
    }

    fun toCompletedExerciseDto(): List<CompletedExerciseDto> {
        if (finishedExercising()) {
            val completedExerciseList = mutableListOf<CompletedExerciseDto>()

            _exerciseList.forEachIndexed { index, exercise ->
                completedExerciseList.add(
                    CompletedExerciseDto(
                        exerciseStatisticsId = exercise.id,
                        completedSets = _exerciseProgressSets[index].filterNotNull().toMutableList()
                    )
                )
            }

            return completedExerciseList
        }

        return emptyList()
    }

    fun selectANonFinishedExercise(exerciseIndex: Int) {
        val isExerciseNotFinished =
            _currentExerciseProgress[exerciseIndex] != DisplayProgressStatus.FINISHED
        val isExerciseNotSelected =
            _currentExerciseProgress[exerciseIndex] != DisplayProgressStatus.SELECTED

        if (isExerciseNotFinished && isExerciseNotSelected) {
            updateSelectionVisually(exerciseIndex)

            _currentExerciseIndex = exerciseIndex
            _currentSetIndex.value = findFirstNullSet(exerciseIndex)
        }
    }

    private fun updateSelectionVisually(exerciseIndex: Int) {
        val newDisplayVisualProgress = _displayVisualProgress.value.copyOf()

        newDisplayVisualProgress[_currentExerciseIndex] =
            _currentExerciseProgress[_currentExerciseIndex]
        newDisplayVisualProgress[exerciseIndex] = DisplayProgressStatus.SELECTED

        _displayVisualProgress.value = newDisplayVisualProgress
    }

    private fun findFirstNullSet(exerciseIndex: Int): Int {
        return _exerciseProgressSets[exerciseIndex].indexOfFirst { it == null }
    }

    fun saveCurrentSetAndGoToNextSet(completedSet: CompletedSetDto) {
        _exerciseProgressSets[_currentExerciseIndex][_currentSetIndex.value] = completedSet

        val isCurrentExerciseSetsCompleted =
            _exerciseProgressSets[_currentExerciseIndex].all { it != null }

        when (isCurrentExerciseSetsCompleted) {
            true -> {
                addToFinishedExercise()
                goToNextExercise()
                setSelectedVisualExercise()
            }
            false -> {
                addToStartedExercise()
                goToNextSet()
            }
        }
    }

    private fun addToFinishedExercise() {
        _currentExerciseProgress[_currentExerciseIndex] = DisplayProgressStatus.FINISHED
        _displayVisualProgress.value[_currentExerciseIndex] = DisplayProgressStatus.FINISHED
    }

    private fun goToNextExercise() {
        if (!finishedExercising()) {

            // Try to find next indexes from current index
            var indexPair = findNextIndexesFromCurrentPosition(
                _currentExerciseIndex,
                _exerciseProgressSets.size
            )

            // If not found, Try to find from first index to the one started before, circling the array.
            if (indexPair.first == -1) {
                indexPair = findNextIndexesFromCurrentPosition(0, _currentExerciseIndex)
            }

            if (indexPair.first != -1) {
                _currentExerciseIndex = indexPair.first
                _currentSetIndex.value = indexPair.second
            }
        }
    }

    private fun findNextIndexesFromCurrentPosition(outerIndex: Int, end: Int): Pair<Int, Int> {
        var outerIndex = outerIndex

        while (outerIndex < end) {
            val innerArray = _exerciseProgressSets[outerIndex]
            for (innerIndex in innerArray.indices) {
                if (innerArray[innerIndex] == null) {
                    // Found
                    return Pair(outerIndex, innerIndex)
                }
            }
            outerIndex++
        }
        // Not found
        return Pair(-1, -1)
    }

    private fun setSelectedVisualExercise() {
        // No se pueden seleccionar ejercicios terminados
        if (_displayVisualProgress.value[_currentExerciseIndex] != DisplayProgressStatus.FINISHED)
            _displayVisualProgress.value[_currentExerciseIndex] = DisplayProgressStatus.SELECTED
    }

    private fun addToStartedExercise() {
        //No es necesario cambiar el estado del display aca, se hace en el momento de seleccion
        _currentExerciseProgress[_currentExerciseIndex] = DisplayProgressStatus.STARTED
    }

    private fun goToNextSet() {
        _currentSetIndex.value = _currentSetIndex.value + 1
    }

    fun finishedExercising(): Boolean {
        return _currentExerciseProgress.all { it == DisplayProgressStatus.FINISHED }
    }

    fun isNotLastSet() =
        _currentSetIndex.value + 1 != _exerciseProgressSets[_currentExerciseIndex].size
}