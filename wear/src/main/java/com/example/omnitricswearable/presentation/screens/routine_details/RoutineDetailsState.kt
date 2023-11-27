package com.example.omnitricswearable.presentation.screens.routine_details

import com.example.omnitricswearable.domain.model.entities.Routine
import com.example.omnitricswearable.domain.model.entities.RoutineDetails

data class RoutineDetailsState(
    val isLoading: Boolean = false,
    val routine: RoutineDetails? = null,
    val error: String = ""
)
