package com.victorbrandalise

import android.app.*
import android.app.Application
import android.content.Intent
import android.os.Build
import android.os.Handler
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.res.ResourcesCompat
import com.victorbrandalise.presentation.HomeActivity
import com.victorbrandalise.presentation.LockscreenActivity

private const val CHANNEL_ID = "heads_up_alerts"
private const val CHANNEL_NAME = "Heads Up Alerts"

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

        val fullScreenIntent = Intent(this, LockscreenActivity::class.java)
        val fullScreenPendingIntent =
            PendingIntent.getActivity(this, 0, fullScreenIntent, 0)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.notifications_active_black_24)
            .setColor(ResourcesCompat.getColor(resources, R.color.purple_200, null))
            .setContentTitle("Heads Up Notification")
            .setAutoCancel(true)
            .setContentIntent(contentPendingIntent)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .build()
    }

}