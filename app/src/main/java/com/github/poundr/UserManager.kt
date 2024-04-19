package com.github.poundr

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.github.poundr.model.AuthResponse
import com.github.poundr.model.LoginEmailRequest
import com.github.poundr.model.Role
import com.github.poundr.network.LoginRestService
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

private const val TAG = "UserManager"

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "userManager")

/* Persistent auth data */
private val PROFILE_ID_KEY = intPreferencesKey("profile_id")
private val XMPP_TOKEN_KEY = stringPreferencesKey("xmpp_token")
private val SESSION_ID_KEY = stringPreferencesKey("session_id")
private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")

@OptIn(DelicateCoroutinesApi::class)
@Singleton
class UserManager @Inject constructor(
    @ApplicationContext private val context: Context,
    private val loginRestService: LoginRestService,
) {
    var isReady: Boolean = false
        private set

    private val _loggedIn = MutableStateFlow(false)
    val loggedIn = _loggedIn.asStateFlow()

    /* Auth data */
    var profileId: Int = 0
        private set
    var xmppToken: String = ""
        private set
    var sessionId: String = ""
        private set
    var authToken: String = ""
        private set

    /* User data */
    var roles: String = "[]"
        private set
    var role: Role = Role.FREE
        private set

    init {
        runBlocking(Dispatchers.IO) {
            val data = context.dataStore.data.first()
            profileId = data[PROFILE_ID_KEY] ?: 0
            xmppToken = data[XMPP_TOKEN_KEY] ?: ""
            sessionId = data[SESSION_ID_KEY] ?: ""
            authToken = data[AUTH_TOKEN_KEY] ?: ""
            _loggedIn.value = profileId != 0 && xmppToken.isNotEmpty() && sessionId.isNotEmpty() && authToken.isNotEmpty()
            isReady = true
        }
    }

    suspend fun login(email: String, password: String) {
        withContext(Dispatchers.IO) {
            val firebaseToken = suspendCoroutine { continuation ->
                FirebaseMessaging.getInstance().token.addOnCompleteListener {
                    if (it.isSuccessful) {
                        continuation.resume(it.result)
                    } else {
                        continuation.resume(null)
                    }
                }
            }

            val response = loginRestService.postSessions(LoginEmailRequest(
                email = email,
                password = password,
                authToken = null,
                token = firebaseToken
            ))

            setAuthResponse(response)
        }
    }

    private suspend fun setAuthResponse(response: AuthResponse) {
        if (
            response.profileId == null || response.profileId == 0 ||
            response.xmppToken.isNullOrEmpty() ||
            response.sessionId.isNullOrEmpty() ||
            response.authToken.isNullOrEmpty()
        ) {
            Log.d(TAG, "setAuthResponse: Invalid response")
            return
        }

        context.dataStore.edit { settings ->
            settings[PROFILE_ID_KEY] = response.profileId
            settings[XMPP_TOKEN_KEY] = response.xmppToken
            settings[SESSION_ID_KEY] = response.sessionId
            settings[AUTH_TOKEN_KEY] = response.authToken
        }

        _loggedIn.value = true
    }
}