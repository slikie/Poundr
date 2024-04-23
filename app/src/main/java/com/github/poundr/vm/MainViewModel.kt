package com.github.poundr.vm

import androidx.annotation.RequiresPermission
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.UserManager
import com.github.poundr.location.GeoHash
import com.github.poundr.location.PoundrLocationManager
import com.google.android.gms.location.LocationRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.minutes

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager,
    private val poundrLocationManager: PoundrLocationManager
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

    @RequiresPermission(anyOf = ["android.permission.ACCESS_COARSE_LOCATION", "android.permission.ACCESS_FINE_LOCATION"])
    suspend fun updateLocation() = withContext(Dispatchers.IO) {
        val request = LocationRequest.Builder(5.minutes.inWholeMilliseconds)
            .setMinUpdateIntervalMillis(5.minutes.inWholeMilliseconds)
            .setMaxUpdateAgeMillis(Long.MAX_VALUE)
            .build()

        poundrLocationManager.getLocationUpdates(request).collect {
            userManager.putLocation(GeoHash.encode(it.latitude, it.longitude, GeoHash.MAX_PRECISION))
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