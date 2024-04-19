package com.github.poundr

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "MyFirebaseMessagingServ"

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: token: $token")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        Log.d(TAG, "onMessageReceived: message received")

        val data = message.data
        for ((key, value) in data) {
            Log.d(TAG, "onMessageReceived: key: $key, value: $value")
        }
    }

    override fun onDeletedMessages() {
        Log.d(TAG, "onDeletedMessages() called")
    }
}