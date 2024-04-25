package com.github.poundr.network

import com.github.poundr.model.ConversationResponse
import com.github.poundr.model.ConversationsResponse
import com.github.poundr.model.InboxFilterRequest
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ConversationService {
    //    @HTTP(hasBody = true, method = HttpDelete.METHOD_NAME, path = "v4/me/muted-profiles")
    //    /* renamed from: A */
    //    Object m13467A(@Body IndividualChatMuteRequest individualChatMuteRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("/v4/chat/conversation/{conversationId}/message-by-id")
    //    /* renamed from: B */
    //    Object m13468B(@Path("conversationId") String str, @Body RefreshMessagesRequest refreshMessagesRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, MessagesResponse>> continuation);
    //
    //    @POST("v4/phrases/frequency/{id}")
    //    /* renamed from: C */
    //    Object m13469C(@Path("id") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chat/conversation/{conversationId}/unpin")
    //    /* renamed from: D */
    //    Object m13470D(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v3/groupchats/{conversationId}")
    //    /* renamed from: E */
    //    Object m13471E(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, GroupChatResponse>> continuation);
    //
    //    @POST("v4/chat/conversation/{conversationId}/read/{messageId}")
    //    /* renamed from: F */
    //    Object m13472F(@Path("conversationId") String str, @Path("messageId") String str2, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chat/conversation/{conversationId}/pin")
    //    /* renamed from: G */
    //    Object m13473G(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("/v4/pics/expiring")
    //    /* renamed from: H */
    //    Object m13474H(@Body ExpiringPhotoReportSentRequest expiringPhotoReportSentRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, ExpiringPhotoStatusResponse>> continuation);
    //
    //    @POST("v5/chat/translate")
    //    /* renamed from: I */
    //    Object m13475I(@Body ChatTranslateRequestV5 chatTranslateRequestV5, Continuation<? super AbstractC10929a<HttpExceptionResponse, ChatTranslateResponseV5>> continuation);
    //
    //    @POST("v4/me/push-conversations")
    //    /* renamed from: J */
    //    Object m13476J(@Body GroupChatMuteRequest groupChatMuteRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @DELETE("v4/chat/conversation/{conversationId}")
    //    /* renamed from: K */
    //    Object m13477K(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @HTTP(hasBody = true, method = HttpDelete.METHOD_NAME, path = "/v4/me/push-conversations")
    //    /* renamed from: L */
    //    Object m13478L(@Body GroupChatMuteRequest groupChatMuteRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chat/message/delete")
    //    /* renamed from: M */
    //    Object m13479M(@Body DeleteMessageRequest deleteMessageRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v3.1/chat/backup")
    //    /* renamed from: N */
    //    Object m13480N(Continuation<? super AbstractC10929a<HttpExceptionResponse, ChatBackupFile>> continuation);
    //
    //    @PATCH("/v3/groupchats/{conversationId}")
    //    /* renamed from: O */
    //    Object m13481O(@Path("conversationId") String str, @Body InviteGroupChatMembersRequest inviteGroupChatMembersRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("/v3/groupchats")
    //    /* renamed from: P */
    //    Object m13482P(@Body CreateGroupRequest createGroupRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, GroupChatResponse>> continuation);
    //
    //    @POST("/v4/recognition/chat")
    //    /* renamed from: Q */
    //    Object m13483Q(@Body OCRResultRequest oCRResultRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @PATCH("/v3/groupchats/{conversationId}/{profileId}")
    //    /* renamed from: R */
    //    Object m13484R(@Path("conversationId") String str, @Path("profileId") String str2, @Body AcceptGroupChatRequest acceptGroupChatRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/me/muted-profiles")
    //    /* renamed from: S */
    //    Object m13485S(@Body IndividualChatMuteRequest individualChatMuteRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chat/message/send")
    //    /* renamed from: a */
    //    Object m13486a(@Body OutboundMessagePayload outboundMessagePayload, Continuation<? super AbstractC10929a<HttpExceptionResponse, MessageResponse>> continuation);
    //
    //    @POST("/v3.1/chat/backup")
    //    /* renamed from: b */
    //    Object m13487b(@Body ChatBackupFileRequest chatBackupFileRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v1/push/conversation/{conversationId}/mute")
    //    /* renamed from: c */
    //    Object m13488c(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("v3/groupchats/all")
    //    /* renamed from: d */
    //    Object m13489d(Continuation<? super AbstractC10929a<HttpExceptionResponse, GroupChatsResponse>> continuation);
    //
    //    @DELETE("/v3/groupchats/{conversationId}/{profileId}")
    //    /* renamed from: e */
    //    Object m13490e(@Path("conversationId") String str, @Path("profileId") String str2, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @DELETE("v3/groupchats/all/{profileId}")
    //    /* renamed from: f */
    //    Object m13491f(@Path("profileId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v1/inbox/conversation")
    //    /* renamed from: g */
    //    Object getConversations(@Body List<String> list, Continuation<? super AbstractC10929a<HttpExceptionResponse, ? extends List<ConversationResponse>>> continuation);
    //
    //    @PATCH("/v3/groupchats/{conversationId}")
    //    /* renamed from: h */
    //    Object m13493h(@Path("conversationId") String str, @Body ChangeGroupChatNameRequest changeGroupChatNameRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("/v3.1/me/push-conversations/{conversationId}")
    //    /* renamed from: i */
    //    Object m13494i(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v1/push/conversation/{conversationId}/unmute")
    //    /* renamed from: j */
    //    Object m13495j(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v4/chat/conversation/{conversationId}/message/{messageId}")
    //    /* renamed from: k */
    //    Object m13496k(@Path("conversationId") String str, @Path("messageId") String str2, Continuation<? super AbstractC10929a<HttpExceptionResponse, SingleMessageResponse>> continuation);
    //
    //    @GET("/v4/pics/expiring/status")
    //    /* renamed from: l */
    //    Object m13497l(Continuation<? super AbstractC10929a<HttpExceptionResponse, ExpiringPhotoStatusResponse>> continuation);
    //
    //    @DELETE("/v3.1/me/push-conversations/{conversationId}")
    //    /* renamed from: m */
    //    Object m13498m(@Path("conversationId") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v3.1/groupchat/canbeinvited")
    //    /* renamed from: n */
    //    Object m13499n(Continuation<? super AbstractC10929a<HttpExceptionResponse, ProfileList>> continuation);
    //
    //    @DELETE("v3/me/prefs/phrases/{id}")
    //    /* renamed from: o */
    //    Object m13500o(@Path("id") String str, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chatstatus/typing")
    //    /* renamed from: p */
    //    Object m13501p(@Body ChatStatusRequest chatStatusRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("v4/chat/message/unsend")
    //    /* renamed from: q */
    //    Object m13502q(@Body ChatUnsendRequest chatUnsendRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @DELETE("/v3.1/chat/backup")
    //    /* renamed from: r */
    //    Object m13503r(Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @POST("/v4/videos/expiring")
    //    /* renamed from: s */
    //    Object m13504s(@Body PrivateVideoReportSentRequest privateVideoReportSentRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, PrivateVideoStatusResponse>> continuation);
    //
    //    @POST("v1/inbox")
    //    /* renamed from: t */
    //    Object m13505t(@Query("page") Integer num, @Body InboxFilterRequest inboxFilterRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, ConversationsResponse>> continuation);
    //
    //    @POST("v4/chat/message/reaction")
    //    /* renamed from: u */
    //    Object m13506u(@Body ChatReactionRequest chatReactionRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v4/chat/conversation/{conversationId}/message")
    //    /* renamed from: v */
    //    Object m13507v(@Path("conversationId") String str, @Query("pageKey") String str2, Continuation<? super AbstractC10929a<HttpExceptionResponse, MessagesResponse>> continuation);
    //
    //    @POST("v3/me/prefs/phrases")
    //    /* renamed from: w */
    //    Object m13508w(@Body AddSavedPhraseRequest addSavedPhraseRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, AddSavedPhraseResponse>> continuation);
    //
    //    @POST("/v4/chats/translate")
    //    /* renamed from: x */
    //    Object m13509x(@Body ChatTranslateRequest chatTranslateRequest, Continuation<? super AbstractC10929a<HttpExceptionResponse, ChatTranslateResponse>> continuation);
    //
    //    @GET("v5/chat/translatable/{conversationId}/{size}")
    //    /* renamed from: y */
    //    Object m13510y(@Path("conversationId") String str, @Path("size") int i2, Continuation<? super AbstractC10929a<HttpExceptionResponse, Unit>> continuation);
    //
    //    @GET("/v4/videos/expiring/status")
    //    /* renamed from: z */
    //    Object m13511z(Continuation<? super AbstractC10929a<HttpExceptionResponse, PrivateVideoStatusResponse>> continuation);

    //    @POST("v1/inbox/conversation")
    //    /* renamed from: g */
    //    Object getConversations(@Body List<String> list, Continuation<? super AbstractC10929a<HttpExceptionResponse, ? extends List<ConversationResponse>>> continuation);
    @POST("v1/inbox/conversation")
    suspend fun getConversations(@Body conversationIds: List<String>): List<ConversationResponse>

    @POST("v1/inbox")
    suspend fun getInbox(@Query("page") page: Int, @Body inboxFilterRequest: InboxFilterRequest): ConversationsResponse
}