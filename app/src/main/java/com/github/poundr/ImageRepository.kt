package com.github.poundr

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.net.HttpURLConnection
import java.net.URL

class ImageRepository(
    private val endpoint: String,
    private val context: Context
) {
    fun getBitmap(url: String): Bitmap {
        val connection = URL(url).openConnection() as HttpURLConnection
        connection.connect()
        val inputStream = connection.inputStream
        return BitmapFactory.decodeStream(inputStream)
    }

    fun getNotificationLargeIconBitmap(mediaHash: String): Bitmap {
        val resources = context.resources
        val width = resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_width)
        val height = resources.getDimensionPixelSize(android.R.dimen.notification_large_icon_height)

        return getBitmap(
            when {
                width <= 75 && height <= 75 -> "$endpoint/images/thumb/75x75/$mediaHash"
                width <= 320 && height <= 320 -> "$endpoint/images/thumb/320x320/$mediaHash"
                width <= 480 && height <= 480 -> "$endpoint/images/profile/480x480/$mediaHash"
                width <= 1024 && height <= 1024 -> "$endpoint/images/profile/1024x1024/$mediaHash"
                else -> "$endpoint/images/profile/2048x2048/$mediaHash"
            }
        )
    }
}