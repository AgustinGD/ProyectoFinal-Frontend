package com.example.omnitricswearable.data.remote.omnitrics.dto.completed

data class CompletedSetDto(
    val repetitions: Int,
    val setNumber: Int,
    val weightInGrams: Long,
    val heartRate: Float,
    val durationTimeInMillis: Long,
    val restTimeInMillis: Long
)