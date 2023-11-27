package com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine

import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("_id")
    val id: String,
    val description: String,
    val dayPosition: Int,
    val exerciseList: List<ExerciseStatistics>
)