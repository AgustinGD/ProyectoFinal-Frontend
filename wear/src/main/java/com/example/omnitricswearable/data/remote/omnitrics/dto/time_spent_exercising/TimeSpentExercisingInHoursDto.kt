package com.example.omnitricswearable.data.remote.omnitrics.dto.time_spent_exercising

import com.google.gson.annotations.SerializedName

data class TimeSpentExercisingInHoursDto(
    @SerializedName("Monday")
    val monday: Float,
    @SerializedName("Tuesday")
    val tuesday: Float,
    @SerializedName("Wednesday")
    val wednesday: Float,
    @SerializedName("Thursday")
    val thursday: Float,
    @SerializedName("Friday")
    val friday: Float,
    @SerializedName("Saturday")
    val saturday: Float,
    @SerializedName("Sunday")
    val sunday: Float
)
