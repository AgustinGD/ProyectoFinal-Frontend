package com.example.omnitricswearable.presentation.screens.statistics.time_spent_exercising

import com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising.TimeSpentExercisingInHoursDto

data class TimeSpentExercisingState(
    val isLoading: Boolean = false,
    val timeSpentExercising: TimeSpentExercisingInHoursDto? = null,
    val error: String = ""
)
