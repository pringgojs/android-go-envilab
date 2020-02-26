package com.envilabindonesia.android.firebase

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.envilabindonesia.android.R
import com.envilabindonesia.android.ui.home.HomeActivity
import com.envilabindonesia.android.util.PrefsUtil
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by galihadityo on 2019-03-27.
 */

class MyFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(p0: RemoteMessage?) {
        super.onMessageReceived(p0)
        p0?.data?.let {
            val defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

            val intent = Intent(applicationContext, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            val pendingIntent = PendingIntent.getActivity(applicationContext, 0, intent, 0)

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val NOTIFICATION_CHANNEL_ID = "101"

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                @SuppressLint("WrongConstant") val notificationChannel =
                    NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification", NotificationManager.IMPORTANCE_MAX)

                //Configure Notification Channel
                notificationChannel.description = this.getString(R.string.envilab_notif)
                notificationChannel.enableLights(true)
                notificationChannel.vibrationPattern = longArrayOf(0, 1000, 500, 1000)
                notificationChannel.enableVibration(true)

                notificationManager.createNotificationChannel(notificationChannel)
            }

            val notificationBuilder = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(it["title"])
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentText(it["body"])
                .setContentIntent(pendingIntent)


            notificationManager.notify(1, notificationBuilder.build())
        }
    }

    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)
        p0?.let {
            PrefsUtil.setFcmToken(it)
        }
    }

}