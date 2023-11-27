package com.example.omnitricswearable.presentation.screens.calendar

import androidx.lifecycle.ViewModel
import com.example.omnitricswearable.domain.notifications.CalendarNotifications
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CalendarViewModel @Inject constructor(
    private val calendarNotifications: CalendarNotifications
) : ViewModel() {

    fun showNotification() = calendarNotifications.showNotification()
}