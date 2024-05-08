package com.github.poundr.vm

import android.util.Log
import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.UserManager
import com.github.poundr.location.GeoHash
import com.github.poundr.location.LocationRepository
import com.github.poundr.network.PoundrWebSocket
import com.google.android.gms.location.LocationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

private const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager,
    private val locationRepository: LocationRepository,
    private val poundrWebSocket: PoundrWebSocket,
) : ViewModel() {
    private val _startDestination = MutableStateFlow(getStartDestination())
    val startDestination = _startDestination.asStateFlow()

    val shouldSendFcmToken: Boolean
        get() = userManager.shouldSendFcmToken

    val isReady: Boolean
        get() = userManager.isReady

    init {
        viewModelScope.launch {
            userManager.loggedIn.collect { loggedIn ->
                _startDestination.value = getStartDestination(loggedIn)
            }
        }
    }

    fun connectWebSocket() {
        poundrWebSocket.connect()
    }

    fun disconnectWebSocket() {
        poundrWebSocket.disconnect()
    }

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun updateLocation() = withContext(Dispatchers.IO) {
        val request = LocationRequest.Builder(5.minutes.inWholeMilliseconds)
            .setMinUpdateIntervalMillis(5.minutes.inWholeMilliseconds)
            .setMaxUpdateAgeMillis(5.minutes.inWholeMilliseconds)
            .build()

        locationRepository.getLocationUpdates(request).collect {
            try {
                userManager.putLocation(GeoHash.encode(it.latitude, it.longitude, GeoHash.MAX_PRECISION))
            } catch (e: Exception) {
                Log.e(TAG, "updateLocation: Failed to update location", e)
            }
        }
    }

    suspend fun sendFcmToken() {
        userManager.pushFcmToken()
    }

    private fun getStartDestination(loggedIn: Boolean = userManager.loggedIn.value): String {
        return if (loggedIn) {
            "main"
        } else {
            "login"
        }
    }
}