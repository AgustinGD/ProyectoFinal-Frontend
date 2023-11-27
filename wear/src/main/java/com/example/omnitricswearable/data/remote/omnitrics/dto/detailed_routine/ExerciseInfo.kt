package com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine

import com.google.gson.annotations.SerializedName

data class ExerciseInfo (
    @SerializedName("_id")
    val id: String,
    val name: String,
    val description: String
)