package com.github.poundr.push

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.github.poundr.ConversationRepository
import com.github.poundr.ImageRepository
import com.github.poundr.R
import com.github.poundr.UserManager
import com.github.poundr.network.model.MessageResponse
import com.github.poundr.persistence.ConversationDao
import com.github.poundr.persistence.UserDao
import com.github.poundr.push.model.PushEvent
import com.github.poundr.push.model.TapsReceivedNotification
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
    @Inject
    lateinit var userDao: UserDao
    @Inject lateinit var conversationDao: ConversationDao
    @Inject
    lateinit var imageRepository: ImageRepository
    @Inject
    lateinit var conversationRepository: ConversationRepository

    private val scope = CoroutineScope(Dispatchers.IO)

    // notifications
    private val notificationIds: MutableMap<Any, Int> = mutableMapOf()
    private var nextNotificationId: Int = 1

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

        scope.launch {
            val type = data["notificationType"] ?: return@launch
            when (type) {
                "PUSH_EVENT" -> {
                    val pushEventText = data["pushEvent"] ?: return@launch
                    val pushEvent = moshi.adapter(PushEvent::class.java).fromJson(pushEventText) ?: return@launch
                    handlePushEvent(pushEvent)
                }
                "chat-platform" -> {
                    val messageJson = data["message"] ?: return@launch
                    val senderName = data["senderDisplayName"]
                    val senderProfileImageMediaHash = data["senderProfileImageMediaHash"]
                    val messageAdapter = moshi.adapter(MessageResponse::class.java)
                    val message = messageAdapter.fromJson(messageJson) ?: return@launch
                    handleChatPlatform(message, senderName, senderProfileImageMediaHash)
                }
                "chat" -> {
                    // Unused in official app
                    Log.d(TAG, "onMessageReceived: received unused \"chat\" notification type")
                }
                "offline-tap-sent-event-v1" -> {
                    val tapJson = data["tap"] ?: return@launch
                    val tapAdapter = moshi.adapter(TapsReceivedNotification::class.java)
                    val tap = tapAdapter.fromJson(tapJson) ?: return@launch
                    handleTap(tap)
                }
                else -> {
                    Log.d(TAG, "onMessageReceived: unknown notification type: $type")
                }
            }
        }
    }

    override fun onDestroy() {
        scope.cancel()
        super.onDestroy()
    }

    private fun getNotificationId(key: Any): Int {
        return notificationIds.getOrPut(key) { nextNotificationId++ }
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

    private suspend fun handleChatPlatform(
        message: MessageResponse,
        senderName: String?,
        senderProfileImageMediaHash: String?
    ) {
        conversationRepository.putMessage(message, senderName, senderProfileImageMediaHash)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notificationManager = NotificationManagerCompat.from(this)

            val text = when (message) {
                is MessageResponse.Album -> "Album received"
                is MessageResponse.Audio -> "Audio received"
                is MessageResponse.ExpiringImage -> "Expiring image received"
                is MessageResponse.Video -> "Video received"
                is MessageResponse.Gaymoji -> "Gaymoji received"
                is MessageResponse.Giphy -> "Giphy received"
                is MessageResponse.Image -> "Image received"
                is MessageResponse.Location -> "Location received"
                is MessageResponse.ProfilePhotoReply -> message.body.photoContentReply
                is MessageResponse.Text -> message.body.text
                is MessageResponse.NonExpiringVideo -> "Non-expiring video received"
                else -> "Unknown message"
            }

            val notification = NotificationCompat.Builder(this, "chats")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(senderName ?: "Someone")
                .setContentText(text)
                .apply {
                    if (senderProfileImageMediaHash != null) {
                        try {
                            val iconBitmap = imageRepository.getNotificationLargeIconBitmap(
                                senderProfileImageMediaHash
                            )
                            setLargeIcon(iconBitmap)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                .setWhen(message.timestamp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()

            notificationManager.notify(getNotificationId("message:${message.conversationId}"), notification)
        }
    }

    private suspend fun handleTap(tap: TapsReceivedNotification) {
        val notificationManager = NotificationManagerCompat.from(this)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            val notification = NotificationCompat.Builder(this, "taps")
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(tap.senderDisplayName ?: "Someone")
                .setContentText("Tap received")
                .apply {
                    if (tap.senderProfileImageHash != null) {
                        try {
                            val iconBitmap =
                                imageRepository.getNotificationLargeIconBitmap(tap.senderProfileImageHash)
                            setLargeIcon(iconBitmap)
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                .setWhen(tap.timestamp)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .build()
            notificationManager.notify(getNotificationId("tap:${tap.senderId}"), notification)
        }
    }
}