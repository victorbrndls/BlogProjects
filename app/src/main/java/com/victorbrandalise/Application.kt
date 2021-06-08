package com.victorbrandalise

import android.app.*
import android.app.Application
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.victorbrandalise.presentation.HomeActivity

private const val CHANNEL_ID = "dgo_alerts"
private const val CHANNEL_NAME = "DGO Notificações"

class Application : Application() {

    private val notificationManager by lazy { NotificationManagerCompat.from(this) }

    override fun onCreate() {
        super.onCreate()

        createNotificationChannel()

        notificationManager.notify(1, createNotification())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH
        ).apply {
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        }

        notificationManager.createNotificationChannel(channel)
    }

    private fun createNotification(): Notification {
        val contentIntent = Intent(this, HomeActivity::class.java)
        val contentPendingIntent =
            PendingIntent.getActivity(this, 0, contentIntent, 0)

        val fullScreenIntent = Intent(this, HomeActivity::class.java)
        val fullScreenPendingIntent =
            PendingIntent.getActivity(this, 0, fullScreenIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_active_purple_24)
            .setContentTitle("Title")
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()
    }

}