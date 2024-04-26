package com.github.poundr.push

import android.util.Log
import com.github.poundr.UserManager
import com.github.poundr.push.model.PushEvent
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.squareup.moshi.Moshi
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "FMService"

@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {
    @Inject
    lateinit var userManager: UserManager
    @Inject
    lateinit var moshi: Moshi

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onNewToken(token: String) {
        Log.d(TAG, "onNewToken: token: $token")
        // TODO: Handle case where request fails (internet connection or server error...)
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

        val type = data["notificationType"] ?: return
        when (type) {
            "PUSH_EVENT" -> {
                val pushEventText = data["pushEvent"] ?: return
                val pushEvent = moshi.adapter(PushEvent::class.java).fromJson(pushEventText) ?: return
                handlePushEvent(pushEvent)
            }
            "chat-platform" -> {
                val messageText = data["message"] ?: return
                // TODO: Parse messageText as json
                handleChatPlatform()
            }
            "chat" -> {
                // Unused in official app
            }
            "offline-tap-sent-event-v1" -> {
                val tapText = data["tap"] ?: return
                // TODO: Parse tapText as json
            }
            else -> {
                Log.d(TAG, "onMessageReceived: unknown notification type ($type)")
            }
        }
    }

    override fun onDeletedMessages() {
        Log.d(TAG, "onDeletedMessages() called")
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun handlePushEvent(pushEvent: PushEvent) {
        val pushEventType = pushEvent.type
        when (pushEventType) {
            "BANNED_PROFILES" -> {}
            "SUPERBOOST_SESSION_END" -> {}
            "BOOST_SESSION_END" -> {}
            "trial_reminder_end" -> {}
            else -> {
                Log.d(
                    TAG,
                    "onMessageReceived: unknown push event type ($pushEventType)"
                )
            }
        }
    }

    private fun handleChatPlatform() {

    }
}