package com.github.poundr

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "MyFirebaseMessagingServ"

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject lateinit var userManager: UserManager

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: token: $token")
        scope.launch {
            if (userManager.loggedIn.value) {
                userManager.pushFcmToken()
            }
        }
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

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }
}