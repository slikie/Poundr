package com.github.poundr.network

import android.app.ActivityManager
import android.content.Context
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.github.poundr.App
import com.github.poundr.UserManager
import dagger.Lazy
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.util.Locale
import java.util.TimeZone
import javax.inject.Inject

class HeaderRequestInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
    private val lazyUserManager: Lazy<UserManager>, // FIXME: Find a way to fix cyclic dependency
) : Interceptor {
    private val activityManager = ContextCompat.getSystemService(context, ActivityManager::class.java)!!

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        val userManager = lazyUserManager.get()

        val isLoggedIn = userManager.loggedIn.value
        if (isLoggedIn) {
            builder.header("Authorization", "Grindr3 ${userManager.sessionId}")
            builder.header("L-Time-Zone", TimeZone.getDefault().id)
            builder.header("L-Grindr-Roles", userManager.roles)
            builder.header("L-Device-Info", getDeviceInfo())
        } else {
            val requireRealDeviceInfo = request.header("requireRealDeviceInfo") == "true"
            if (requireRealDeviceInfo) {
                builder.header("L-Time-Zone", TimeZone.getDefault().id)
                builder.header("L-Device-Info", getDeviceInfo())
            } else {
                builder.header("L-Time-Zone", "Unknown")
            }
        }

        builder.header("Accept", "application/json")
        builder.header("User-Agent", getUserAgent())
        val locale = getLocale()
        builder.header("L-Locale", getLocaleString(locale))
        builder.header("Accept-language", locale.toLanguageTag())

        return chain.proceed(builder.build())
    }

    private fun getDeviceInfo(): String {
        val androidId = Settings.Secure.getString(context.getContentResolver(), "android_id")
        val memoryInfo = ActivityManager.MemoryInfo().apply {
            activityManager.getMemoryInfo(this)
        }
        val displayMetrics = context.resources.displayMetrics
        val advertisingId = "00000000-0000-0000-0000-000000000000"

        return "$androidId;GLOBAL;2;${memoryInfo.totalMem};${displayMetrics.heightPixels}x${displayMetrics.widthPixels};$advertisingId"
    }

    private fun getUserAgent(): String {
        val userManager = lazyUserManager.get()
        val versionName = App.SPOOFED_VERSION_NAME
        val versionCode = App.SPOOFED_VERSION_CODE
        val userRoleTitleCase = userManager.role.nameTitleCase
        val versionRelease = Build.VERSION.RELEASE
        val model = fixString(Build.MODEL)
        val manufacturer = fixString(Build.MANUFACTURER)

        return "grindr3/$versionName.$versionCode;$versionCode;$userRoleTitleCase;Android $versionRelease;$model;$manufacturer"
    }

    private fun getLocale(): Locale {
        if (Build.VERSION.SDK_INT >= 24) {
            val locales = context.resources.configuration.locales
            if (locales.size() > 0) return locales.get(0)
        }
        return Locale.ENGLISH
    }

    private fun getLocaleString(locale: Locale): String {
        val country = locale.country
        val language = locale.language
        return if (country.isNullOrBlank()) {
            language
        } else {
            "${language}_${country}"
        }
    }

    private fun fixString(str: String): String {
        return if (str.any { (it < ' ' && it != '\t') || it > '~' }) {
            str.toByteArray().toString()
        } else {
            str
        }
    }
}