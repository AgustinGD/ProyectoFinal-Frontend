package com.example.omnitricswearable.data.remote.omnitrics.dto.simple_routine

import com.example.omnitricswearable.domain.model.entities.Routine
import com.google.gson.annotations.SerializedName
import java.util.*

data class RoutineDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val isFavourite: Boolean,
    val createdAt: Date
)

fun RoutineDto.toRoutineDetails(): Routine {
    return Routine(
        id = id,
        name = name,
        isFavourite = isFavourite,
        createdAt = createdAt
    )
}
