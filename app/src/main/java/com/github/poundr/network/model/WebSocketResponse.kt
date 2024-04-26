package com.github.poundr.network.model

import com.squareup.moshi.JsonClass

interface WebSocketResponse {

    @JsonClass(generateAdapter = true)
    class ConnectionEstablished : WebSocketResponse

    object Unknown : WebSocketResponse

    companion object {
        const val KEY = "type"
        const val CONNECTION_ESTABLISHED = "ws.connection.established"
        const val TAP_V1_TAP_SENT = "tap.v1.tap_sent"
        const val TAP_V2_TAP_SENT = "tap.v2.tap_sent"
        const val VIDEOCALL_V1_CALL_ENDED = "videocall.v1.call_ended"
        const val VIDEOCALL_V1_INCOMING_CALL = "videocall.v1.incoming_call"
        const val VIEWED_ME_V1_NEW_VIEW_RECEIVED = "viewed_me.v1.new_view_received"
        const val CHAT_V1_CONVERSATION_DELETE = "chat.v1.conversation.delete"
        const val CHAT_V1_CONVERSATION_UPDATE = "chat.v1.conversation.update"
        const val CHAT_V1_MESSAGE_SEND_RESPONSE = "chat.v1.message.send.response"
        const val CHAT_V1_REFRESH_DYNAMIC = "chat.v1.refresh_dynamic"
        const val CHAT_V1_MESSAGE_DELETED = "chat.v1.message_deleted"
        const val CHAT_V1_CONVERSATION_READ = "chat.v1.conversation_read"
        const val CHAT_V1_MESSAGE_SENT = "chat.v1.message_sent"
        const val CHAT_V1_CACHE_BOMB_INBOX = "chat.v1.cache_bomb.inbox"
        const val CHAT_V1_TYPING_STATUS = "chat.v1.typing_status"
    }
}