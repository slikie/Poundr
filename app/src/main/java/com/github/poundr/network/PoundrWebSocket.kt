package com.github.poundr.network

import android.util.Log
import com.github.poundr.network.model.WebSocketResponse
import com.squareup.moshi.Moshi
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

private const val TAG = "PoundrWebSocket"

class PoundrWebSocket(
    moshi: Moshi
) {
    private val adapter = moshi.adapter(WebSocketResponse::class.java)

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG, "WebSocket connection opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            val response = adapter.fromJson(text) ?: return
            when (response) {
                is WebSocketResponse.ConnectionEstablished -> {
                    Log.d(TAG, "WebSocket connection established")
                }
                is WebSocketResponse.Unknown -> {
                    Log.w(TAG, "Received unknown response: $text")
                }
            }
        }

        override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
            Log.w(TAG, "Received unexpected binary message")
        }

        override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
            Log.d(TAG, "WebSocket connection closing with code $code and reason $reason")
            webSocket.close(code, reason)
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            when (code) {
                // normal closure
                1000 -> {
                    Log.d(TAG, "WebSocket connection closed normally")
                }
                // unauthorized, invalid token
                3000, 4401 -> {
                    Log.w(TAG, "WebSocket connection closed due to unauthorized access")
                }
                // unknown
                else -> {
                    Log.w(TAG, "WebSocket connection closed with code $code and reason $reason")
                }
            }
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket connection failed", t)
            // TODO: handle failure
        }
    }
}