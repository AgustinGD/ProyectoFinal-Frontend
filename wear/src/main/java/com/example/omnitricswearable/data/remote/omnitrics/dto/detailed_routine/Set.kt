package com.example.omnitricswearable.data.remote.omnitrics.dto.detailed_routine

import com.google.gson.annotations.SerializedName

data class Set(
    @SerializedName("_id")
    val id: String,
    val setNumber: Int,
    val repetitions: Int,
    val weightInGrams: Long,
    val heartRate: Double,
    val durationTimeInMillis: Long,
    val restTimeInMillis: Long,
    val creationDate: String
)
