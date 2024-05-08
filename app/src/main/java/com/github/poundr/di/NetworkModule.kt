package com.github.poundr.di

import android.content.Context
import com.github.poundr.ConversationRepository
import com.github.poundr.ImageRepository
import com.github.poundr.network.ConversationService
import com.github.poundr.network.GrindrAuthenticator
import com.github.poundr.network.HeaderRequestInterceptor
import com.github.poundr.network.LoginRestService
import com.github.poundr.network.PoundrWebSocket
import com.github.poundr.network.ServerDrivenCascadeService
import com.github.poundr.network.SettingsRestService
import com.github.poundr.network.model.MessageResponse
import com.github.poundr.network.model.ServerDrivenCascadeApiItem
import com.github.poundr.network.model.WebSocketResponse
import com.github.poundr.persistence.PoundrDatabase
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    private val BASE_SERVICE_ENDPOINT = "https://grindr.mobi/"
    private val GAYMOJI_SERVICE_ENDPOINT = "https://cdns.grindr.com:443"
    private val MEDIA_CDN_ENDPOINT = "https://cdns.grindr.com:443"
    private val PRESENCE_ENDPOINT = "wss://presence.grindr.com:443"
    private val SPOTIFY_AUTH_API_ENDPOINT = "https://accounts.spotify.com/"
    private val SPOTIFY_API_ENDPOINT = "https://api.spotify.com/"
    private val GIPHY_SEARCH_ENDPOINT = "https://api.giphy.com"
    private val WS_ENDPOINT = "wss://grindr.mobi/v1/ws"

    @Provides
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(
                PolymorphicJsonAdapterFactory.of(ServerDrivenCascadeApiItem::class.java, ServerDrivenCascadeApiItem.KEY)
                    .withSubtype(ServerDrivenCascadeApiItem.FavoritesHeader::class.java, ServerDrivenCascadeApiItem.FAVS_HEADER)
                    .withSubtype(ServerDrivenCascadeApiItem.FavoritesNoFreeResults::class.java, ServerDrivenCascadeApiItem.FAVS_NO_FREE_RESULTS)
                    .withSubtype(ServerDrivenCascadeApiItem.FavoritesNoXtraResults::class.java, ServerDrivenCascadeApiItem.FAVS_NO_XTRA_RESULTS)
                    .withSubtype(ServerDrivenCascadeApiItem.PartialProfile::class.java, ServerDrivenCascadeApiItem.PARTIAL_PROFILE)
                    .withSubtype(ServerDrivenCascadeApiItem.Profile::class.java, ServerDrivenCascadeApiItem.FULL_PROFILE)
                    .withDefaultValue(ServerDrivenCascadeApiItem.Unknown)
            )
            .add(
                PolymorphicJsonAdapterFactory.of(WebSocketResponse::class.java, WebSocketResponse.KEY)
                    .withSubtype(WebSocketResponse.ConnectionEstablished::class.java, WebSocketResponse.CONNECTION_ESTABLISHED)
                    .withSubtype(WebSocketResponse.TapReceived::class.java, WebSocketResponse.TAP_V1_TAP_SENT)
                    .withSubtype(WebSocketResponse.TapReceived::class.java, WebSocketResponse.TAP_V2_TAP_SENT)
                    .withSubtype(WebSocketResponse.ViewReceived::class.java, WebSocketResponse.VIEWED_ME_V1_NEW_VIEW_RECEIVED)
                    .withSubtype(WebSocketResponse.MessageRead::class.java, WebSocketResponse.CHAT_V1_CONVERSATION_READ)
                    .withSubtype(WebSocketResponse.MessageReceived::class.java, WebSocketResponse.CHAT_V1_MESSAGE_SENT)
                    .withSubtype(WebSocketResponse.TypingStatus::class.java, WebSocketResponse.CHAT_V1_TYPING_STATUS)
                    .withDefaultValue(WebSocketResponse.Unknown)
            )
            .add(
                PolymorphicJsonAdapterFactory.of(MessageResponse::class.java, MessageResponse.KEY)
                    .withSubtype(MessageResponse.Album::class.java, MessageResponse.ALBUM)
                    .withSubtype(MessageResponse.AlbumContentReaction::class.java, MessageResponse.ALBUM_CONTENT_REACTION)
                    .withSubtype(MessageResponse.AlbumContentReply::class.java, MessageResponse.ALBUM_CONTENT_REPLY)
                    .withSubtype(MessageResponse.Audio::class.java, MessageResponse.AUDIO)
                    .withSubtype(MessageResponse.ExpiringImage::class.java, MessageResponse.EXPIRING_IMAGE)
                    .withSubtype(MessageResponse.Video::class.java, MessageResponse.VIDEO)
                    .withSubtype(MessageResponse.Gaymoji::class.java, MessageResponse.GAYMOJI)
                    .withSubtype(MessageResponse.Giphy::class.java, MessageResponse.GIPHY)
                    .withSubtype(MessageResponse.Image::class.java, MessageResponse.IMAGE)
                    .withSubtype(MessageResponse.Location::class.java, MessageResponse.LOCATION)
                    .withSubtype(MessageResponse.ProfilePhotoReply::class.java, MessageResponse.PROFILE_PHOTO_REPLY)
                    .withSubtype(MessageResponse.Retract::class.java, MessageResponse.RETRACT)
                    .withSubtype(MessageResponse.Text::class.java, MessageResponse.TEXT)
                    .withSubtype(MessageResponse.Unknown::class.java, MessageResponse.UNKNOWN)
                    .withSubtype(MessageResponse.NonExpiringVideo::class.java, MessageResponse.NON_EXPIRING_VIDEO)
                    .withDefaultValue(MessageResponse.Unknown())
            )
            .build()
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_SERVICE_ENDPOINT)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }


    @Provides
    @Singleton
    fun provideOkHttpClient(
        headerRequestInterceptor: HeaderRequestInterceptor,
        grindrAuthenticator: GrindrAuthenticator,
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .followRedirects(false)
            .followSslRedirects(false)
            .addInterceptor(headerRequestInterceptor)
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .authenticator(grindrAuthenticator)
            .build()
    }

    @Provides
    @Singleton
    fun provideLoginRestService(retrofit: Retrofit, moshi: Moshi): LoginRestService {
        return retrofit.create(LoginRestService::class.java)
    }

    @Provides
    @Singleton
    fun provideServerDrivenCascadeService(retrofit: Retrofit): ServerDrivenCascadeService {
        return retrofit.create(ServerDrivenCascadeService::class.java)
    }

    @Provides
    @Singleton
    fun provideSettingsRestService(retrofit: Retrofit): SettingsRestService {
        return retrofit.create(SettingsRestService::class.java)
    }

    @Provides
    @Singleton
    fun provideImageRepository(
        @ApplicationContext context: Context,
    ): ImageRepository {
        return ImageRepository(MEDIA_CDN_ENDPOINT, context)
    }

    @Provides
    @Singleton
    fun provideConversationRepository(
        poundrDatabase: PoundrDatabase
    ): ConversationRepository {
        return ConversationRepository(poundrDatabase)
    }

    @Provides
    @Singleton
    fun provideChatRestService(retrofit: Retrofit): ConversationService {
        return retrofit.create(ConversationService::class.java)
    }

    @Provides
    @Singleton
    fun providePoundrWebSocket(
        client: OkHttpClient,
        moshi: Moshi,
        conversationRepository: ConversationRepository
    ): PoundrWebSocket {
        return PoundrWebSocket(WS_ENDPOINT, client, moshi, conversationRepository)
    }
}