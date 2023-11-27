package com.example.omnitricswearable.domain.model.stopwatch

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*

class StopWatch {
    private var coroutineScope = CoroutineScope(Dispatchers.Main)
    private var isActive = false

    private var _timeMillis = MutableStateFlow(0L)
    val timeMillis = _timeMillis.asStateFlow()

    private var lastTimestamp = 0L

    fun start() {
        if(isActive) return

        coroutineScope.launch {
            lastTimestamp = System.currentTimeMillis()
            this@StopWatch.isActive = true
            while(this@StopWatch.isActive) {
                delay(10L)
                _timeMillis.value += System.currentTimeMillis() - lastTimestamp
                lastTimestamp = System.currentTimeMillis()
            }
        }
    }

    fun pause() {
        isActive = false
    }

    fun reset() {
        coroutineScope.cancel()
        coroutineScope = CoroutineScope(Dispatchers.Main)
        _timeMillis.value = 0L
        lastTimestamp = 0L
        isActive = false
    }

    fun formatTime(timeMillis: Long): String {
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