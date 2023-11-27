package com.example.omnitricswearable.presentation.screens.statistics.time_spent_exercising

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.domain.use_case.get_time_spent_exercising.GetTimeSpentExercisingUseCase
import com.example.omnitricswearable.presentation.screens.routine_details.RoutineDetailsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class TimeSpentExercisingViewModel @Inject constructor(
    private val getTimeSpentExercisingUseCase: GetTimeSpentExercisingUseCase
) : ViewModel() {

    private val _timeSpentExercisingState = MutableStateFlow(TimeSpentExercisingState())
    val timeSpentExercisingState = _timeSpentExercisingState.asStateFlow()

    init {
        getTimeSpentExercising()
    }

    private fun getTimeSpentExercising() {
        getTimeSpentExercisingUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _timeSpentExercisingState.value = TimeSpentExercisingState(timeSpentExercising = result.data)
                }
                is Resource.Error -> {
                    _timeSpentExercisingState.value = TimeSpentExercisingState(
                        error = result.message ?: "Error inesperado"
                    )
                }
                is Resource.Loading -> {
                    _timeSpentExercisingState.value = TimeSpentExercisingState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

}