package com.github.poundr.network

import com.github.poundr.model.ConversationsResponse
import com.github.poundr.model.InboxFilterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ChatRestService {
    @POST("v1/inbox")
    suspend fun getInbox(@Query("page") page: Int, @Body inboxFilterRequest: InboxFilterRequest): ConversationsResponse
}