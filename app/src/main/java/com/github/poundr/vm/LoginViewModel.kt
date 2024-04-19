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
class LoginViewModel @Inject constructor(
    private val userManager: UserManager
) : ViewModel() {
    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _loggingIn = MutableStateFlow(false)
    val loggingIn = _loggingIn.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(password: String) {
        _password.value = password
    }

    fun login() {
        _loggingIn.value = true
        viewModelScope.launch {
            try {
                userManager.login(_email.value, _password.value)
            } catch (e: Exception) {
                // Handle error
            }
        }.invokeOnCompletion {
            _loggingIn.value = false
        }
    }
}