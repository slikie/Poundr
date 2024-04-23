package com.github.poundr.di

import com.github.poundr.ImageRepository
import com.github.poundr.model.ServerDrivenCascadeApiItem
import com.github.poundr.network.ChatRestService
import com.github.poundr.network.GrindrAuthenticator
import com.github.poundr.network.HeaderRequestInterceptor
import com.github.poundr.network.LoginRestService
import com.github.poundr.network.ServerDrivenCascadeService
import com.github.poundr.network.SettingsRestService
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
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
    private val CHAT_HOST_ENDPOINT = "xmpps://chat.grindr.com:453"
    private val SPOTIFY_AUTH_API_ENDPOINT = "https://accounts.spotify.com/"
    private val SPOTIFY_API_ENDPOINT = "https://api.spotify.com/"
    private val GIPHY_SEARCH_ENDPOINT = "https://api.giphy.com"

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
                    .withDefaultValue(ServerDrivenCascadeApiItem.Unknown())
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
    fun provideImageRepository(): ImageRepository {
        return ImageRepository(MEDIA_CDN_ENDPOINT)
    }

    @Provides
    @Singleton
    fun provideChatRestService(retrofit: Retrofit): ChatRestService {
        return retrofit.create(ChatRestService::class.java)
    }
}