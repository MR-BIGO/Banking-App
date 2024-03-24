package com.example.bankingapp.presentation.notification

import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.bankingapp.R

class NotificationService(private val context: Context, private val merchName: String) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val notification = NotificationCompat.Builder(context, NOTIFICATION_ID)
            .setSmallIcon(R.drawable.ic_bank)
            .setContentTitle("Payment Successful")
            .setContentText("Your payment to $merchName \nhas been successful")
            .setAutoCancel(true)
            .build()

        notificationManager.notify(1, notification)
    }
    companion object {
        const val NOTIFICATION_ID = "congrats_channel"
    }
}