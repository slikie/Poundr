package com.github.poundr.network

import com.github.poundr.UserManager
import com.github.poundr.utils.Jwt
import dagger.Lazy
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Headers
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class GrindrAuthenticator @Inject constructor(
    private val lazyUserManager: Lazy<UserManager>,
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val userManager = lazyUserManager.get()

        // Check if the token is expired
        if (!Jwt.isExpired(userManager.sessionId)) return null

        // Avoid multiple threads refreshing the token
        synchronized(this) {
            // Check if the token is still expired
            if (Jwt.isExpired(userManager.sessionId)) {
                // Refresh the token
                runBlocking { userManager.refreshToken() }
            }
        }

        // Put the new tokens in the headers, in the same order as the original request
        val headers = Headers.Builder()
            .apply {
                response.request.headers.forEach { (name, value) ->
                    if (name == "Authorization") {
                        add(name, "Grindr3 ${userManager.sessionId}")
                    } else {
                        add(name, value)
                    }
                }
            }
            .build()

        // Return the new request
        return response.request.newBuilder()
            .headers(headers)
            .build()
    }
}