package com.example.omnitricswearable.data.remote.omnitrics.dto.completed

import com.google.gson.annotations.SerializedName

data class CompletedExerciseDto(
    val exerciseStatisticsId: String,
    @SerializedName("setList")
    val completedSets: MutableList<CompletedSetDto>
)