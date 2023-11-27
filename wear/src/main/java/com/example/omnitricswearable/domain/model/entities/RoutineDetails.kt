package com.example.omnitricswearable.domain.model.entities

import com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine.Day

data class RoutineDetails(
    val id: String,
    val name: String,
    val isFavourite: Boolean,
    val dayList: List<Day>,
    val updatedDate: String,
)
