package com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine

import com.google.gson.annotations.SerializedName

data class ExerciseStatistics(
    @SerializedName("_id")
    val id: String,
    val exerciseInfo: ExerciseInfo,
    val maxSets: Int,
    val position: Int,
    val setList: List<Set>
)
