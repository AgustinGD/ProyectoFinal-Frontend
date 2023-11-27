package com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine

import com.example.omnitricswearable.domain.model.entities.RoutineDetails
import com.google.gson.annotations.SerializedName

data class RoutineDetailsDto(
    @SerializedName("_id")
    val id: String,
    val name: String,
    val isFavourite: Boolean,
    val dayList: List<Day>,
    val createdAt: String,
    val updatedAt: String,
)


fun RoutineDetailsDto.toRoutineDetails(): RoutineDetails {
    return RoutineDetails(
        id = id,
        name = name,
        isFavourite = isFavourite,
        dayList = dayList,
        updatedDate = updatedAt
    )
}