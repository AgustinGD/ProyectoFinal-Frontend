package com.example.omnitricswearable.presentation.screens.routine_list

import com.example.omnitricswearable.domain.model.entities.Routine

data class RoutineListState(
    val isLoading: Boolean = false,
    val routines: List<Routine> = emptyList(),
    val error: String = ""
)
