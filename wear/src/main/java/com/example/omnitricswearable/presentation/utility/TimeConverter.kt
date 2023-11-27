package com.example.omnitricswearable.presentation.utility

import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class TimeConverter {
    companion object {
        fun formatMillisToString(timeMillis: Long): String {
            val localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(timeMillis),
                ZoneId.systemDefault()
            )
            val formatter = DateTimeFormatter.ofPattern(
                "mm:ss:SS",
                Locale.getDefault()
            )
            return localDateTime.format(formatter)
        }
    }
}

