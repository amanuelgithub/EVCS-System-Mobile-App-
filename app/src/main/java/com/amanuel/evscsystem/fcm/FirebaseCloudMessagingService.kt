package com.amanuel.evscsystem.fcm

import android.util.Log
import androidx.navigation.NavDeepLinkBuilder
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.amanuel.evscsystem.R
import com.amanuel.evscsystem.notification.NotificationHelper
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FirebaseCloudMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "MyFirebaseMsgService" // used for logging data
        private const val TAG_FCM_MESSAGE = "MessageFromFCM"
        var notificaitonId: Int = 0
    }


    // [START receive_message]
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        // TODO(developer): Handle FCM messages here.

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob()
            } else {
                // Handle message within 10 seconds
                handleNow()
            }
        }

        // Check if message contains a notification payload.
        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")

        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        if (remoteMessage.data.isNotEmpty()) {
            // create the deep link using the pending intent
            // optionally we can pass in an argument using setArgument method
            val pendingIntent = NavDeepLinkBuilder(this)
                .setGraph(R.navigation.nav_graph_main)
//                .setDestination(R.id.notificationsDetailFragment)
                .setDestination(R.id.notificationsFragment)
                .createPendingIntent()


            NotificationHelper.postNotification(
                notificaitonId,
                remoteMessage.data["title"].toString(),
                remoteMessage.data["body"].toString(),
                this,
                pendingIntent
            )
        }
    }
    // [END receive_message]

    // [START on_new_token]
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
    }
    // [END on_new_token]


    /**
     * Schedule async work using WorkManager.
     */
    private fun scheduleJob() {
        // [START dispatch_job]
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java).build()
        WorkManager.getInstance().beginWith(work).enqueue()
        // [END dispatch_job]
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }

}



















