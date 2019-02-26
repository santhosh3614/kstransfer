package com.hb.silverlining.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.text.TextUtils
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.hb.silverlining.AppConstants
import com.hb.silverlining.R
import com.hb.silverlining.activities.SplashActivity
import com.hb.silverlining.core.LifecycleHandler
import com.hb.silverlining.utils.LOGApp
import com.hb.silverlining.utils.SharedPreferenceUtil

/**
 * @author RJ
 */
class MessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        super.onMessageReceived(remoteMessage)
        if (SharedPreferenceUtil.getLoginUserData(this).userId == null) return
        remoteMessage?.let {
            SharedPreferenceUtil.updateNotificationCount(this)
            LOGApp.e("onMessageReceived", it.data.toString())
            if (it.data.containsKey(AppConstants.PushNotification.KEY_OTHERS)) {
                if (LifecycleHandler.isApplicationInForeground()) {
                    sendBroadCast(it.data[AppConstants.PushNotification.KEY_OTHERS]!!)
                }
                generateNotification(it)
            }
        }
    }

    private fun generateNotification(remoteMessage: RemoteMessage) {
        val mBuilder: NotificationCompat.Builder = NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
        mBuilder.setLargeIcon(BitmapFactory.decodeResource(resources, if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) R.mipmap.ic_launcher_round else R.mipmap.ic_launcher))
        mBuilder.setSmallIcon(R.drawable.push_ic_launcher_small)
        mBuilder.setContentTitle(if (TextUtils.isEmpty(remoteMessage.data[AppConstants.PushNotification.KEY_TITLE])) getString(R.string.app_name) else remoteMessage.data[AppConstants.PushNotification.KEY_TITLE])
        mBuilder.setContentText(remoteMessage.data[AppConstants.PushNotification.KEY_MESSAGE])
        mBuilder.setContentIntent(getPendingIntent(remoteMessage.data[AppConstants.PushNotification.KEY_OTHERS]!!))
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        /**
         * Android 8 Notification channel configuration
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_HIGH
            val mChannel = NotificationChannel(getString(R.string.notification_channel_id), getString(R.string.notification_channel_id), importance)
            notificationManager.createNotificationChannel(mChannel)
        }

        mBuilder.setAutoCancel(true)
        notificationManager.notify(AppConstants.PushNotification.REQ_NOTIFIER_ID, mBuilder.build())
    }

    private fun getPendingIntent(pushData: String): PendingIntent {
        var intent = Intent(this, SplashActivity::class.java)
        if (LifecycleHandler.getCurrentActivity() != null)
            intent = Intent(this, LifecycleHandler.getCurrentActivity()?.javaClass)
        intent.putExtra(AppConstants.PushNotification.ARG_PUSH_BUNDLE, pushData)
        return PendingIntent.getActivity(this, AppConstants.PushNotification.REQ_INTENT_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    private fun sendBroadCast(pushData: String) {
        val pushDataIntent = Intent(AppConstants.LocalBroadCasts.NOTIFICATION_RECEIVE_ON_FOREGROUND)
        pushDataIntent.putExtra(AppConstants.PushNotification.ARG_PUSH_BUNDLE, pushData)
        sendBroadcast(pushDataIntent)
    }
}