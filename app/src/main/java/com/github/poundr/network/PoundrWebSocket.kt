package com.github.poundr.network

import android.util.Log
import com.github.poundr.ConversationRepository
import com.github.poundr.network.model.WebSocketRequest
import com.github.poundr.network.model.WebSocketResponse
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

private const val TAG = "PoundrWebSocket"

class PoundrWebSocket(
    private val endpoint: String,
    private val client: OkHttpClient,
    moshi: Moshi,
    private val conversationRepository: ConversationRepository
) {
    private val requestAdapter = moshi.adapter(WebSocketRequest::class.java)
    private val responseAdapter = moshi.adapter(WebSocketResponse::class.java)

    private var webSocket: WebSocket? = null

    private val listener = object : WebSocketListener() {
        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d(TAG, "WebSocket connection opened")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d(TAG, "Received message: $text")
            val response = responseAdapter.fromJson(text)
            if (response == null) {
                Log.w(TAG, "Failed to parse response: $text")
                return
            }

            when (response) {
                is WebSocketResponse.ConnectionEstablished -> {
                    Log.d(TAG, "WebSocket connection established")
                }
                is WebSocketResponse.TapReceived -> {
                    Log.d(TAG, "Received tap sent response: ${response.payload}")
                }
                is WebSocketResponse.ViewReceived -> {
                    Log.d(TAG, "Received new view: ${response.payload}")
                }
                is WebSocketResponse.MessageRead -> {
                    Log.d(TAG, "Received message read response: ${response.payload}")
                }
                is WebSocketResponse.MessageReceived -> {
                    Log.d(TAG, "Received message: ${response.payload}")
                }
                is WebSocketResponse.TypingStatus -> {
                    Log.d(TAG, "Received typing status: ${response.payload}")
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
            this@PoundrWebSocket.webSocket = null
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
            Log.e(TAG, "WebSocket connection failed, response: $response", t)

            if (response != null) {
                when (response.code) {
                    401 -> {
                        // invalid session
                        Log.d(TAG, "onFailure: Invalid session")
                    }
                    else -> {
                        // unknown error
                    }
                }
            }
            // TODO: handle failure
            this@PoundrWebSocket.webSocket = null
        }
    }

    fun connect() {
        Log.d(TAG, "connect: Connecting to websocket")
        if (this.webSocket != null) {
            Log.w(TAG, "WebSocket connection is already open")
            return
        }

        val request = Request.Builder()
            .url(endpoint)
            .build()

        this.webSocket = client.newWebSocket(request, listener)
    }

    fun send(request: WebSocketRequest) {
        Log.d(TAG, "send: Sending message to $endpoint: $request")

        val webSocket = this.webSocket
        if (webSocket == null) {
            Log.w(TAG, "WebSocket connection is not open")
            return
        }

        val text = requestAdapter.toJson(request)
        webSocket.send(text)
    }

    fun disconnect() {
        Log.d(TAG, "disconnect: Disconnecting from websocket")

        val webSocket = this.webSocket
        if (webSocket == null) {
            Log.w(TAG, "WebSocket connection is not open")
            return
        }

        webSocket.close(1000, "Normal closure")

        // Set webSocket to null straight away to avoid race conditions
        this.webSocket = null
    }
}