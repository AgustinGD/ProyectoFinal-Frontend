package com.example.omnitricswearable.presentation.screens.routine_list

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.omnitricswearable.common.Resource
import com.example.omnitricswearable.domain.use_case.get_routines.GetRoutinesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class RoutineListViewModel @Inject constructor(
    private val getRoutinesUseCase: GetRoutinesUseCase
) : ViewModel() {

    private var _updateRoutineList = true

    private val _routineListState = mutableStateOf(RoutineListState())
    val routineListState: State<RoutineListState> = _routineListState

    fun updateRoutineList(){
        if(_updateRoutineList){
            getRoutineList()
            _updateRoutineList = false
        }
    }

    fun getRoutineList() {
        getRoutinesUseCase().onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _routineListState.value =
                        RoutineListState(routines = result.data ?: emptyList())
                }
                is Resource.Error -> {
                    _routineListState.value = RoutineListState(
                        error = result.message ?: "Error inesperado"
                    )
                }
                is Resource.Loading -> {
                    _routineListState.value = RoutineListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }

    fun resetUpdateRoutineList(){
        _updateRoutineList = true
    }
}