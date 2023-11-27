package com.example.omnitricswearable.presentation.screens.routine_details.exercise_session.utils

import androidx.compose.ui.graphics.Color

object WorkoutStatusColor {
    fun getColor(status: WorkoutStatus): Color {
        return when (status) {
            WorkoutStatus.FINISHED -> Color(0xff109648)
            WorkoutStatus.STOPPED -> Color(0xff6b6570)
            WorkoutStatus.PLAYING -> Color(0xff1b98e0)
            WorkoutStatus.PAUSED -> Color(0xfffef6c9)
        }
    }
}