package com.github.poundr.vm

import androidx.lifecycle.ViewModel
import com.github.poundr.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(
    private val userManager: UserManager,
) : ViewModel() {

    suspend fun sendFcmToken() {
        userManager.pushFcmToken()
    }
}