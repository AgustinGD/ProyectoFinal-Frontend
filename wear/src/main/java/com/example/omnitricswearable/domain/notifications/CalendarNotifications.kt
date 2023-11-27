package com.example.omnitricswearable.domain.notifications

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class CalendarNotifications(
    private val appContext: Context
) {
    fun showNotification() {

        val notification = NotificationCompat.Builder(appContext, "channel_id")
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Calendario de Rutinas")
            .setContentText("TEst!")
            .build()
        val notificationManager = appContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notification)
    }
}