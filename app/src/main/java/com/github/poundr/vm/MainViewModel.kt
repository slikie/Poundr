package com.github.poundr.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userManager: UserManager,
) : ViewModel() {
    private val _startDestination = MutableStateFlow(getStartDestination())
    val startDestination = _startDestination.asStateFlow()

    val isReady: Boolean
        get() = userManager.isReady

    init {
        viewModelScope.launch {
            userManager.loggedIn.collect { loggedIn ->
                _startDestination.value = getStartDestination(loggedIn)
            }
        }
    }

    private fun getStartDestination(loggedIn: Boolean = userManager.loggedIn.value): String {
        return if (loggedIn) {
            "main"
        } else {
            "login"
        }
    }
}