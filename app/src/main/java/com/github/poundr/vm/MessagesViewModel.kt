package com.github.poundr.vm

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.model.ConversationResponse
import com.github.poundr.model.InboxFilterRequest
import com.github.poundr.network.ChatRestService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessagesViewModel @Inject constructor(
    private val chatRestService: ChatRestService
) : ViewModel() {
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _messages = MutableStateFlow(emptyList<ConversationResponse>())
    val messages = _messages.asStateFlow()

    fun refresh() {
        _isRefreshing.value = true
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = chatRestService.getInbox(1, InboxFilterRequest(null, null, null))
                _messages.value = response.entries
            } catch (e: Exception) {
                Log.e("MessagesViewModel", "Failed to refresh messages", e)
            }
            _isRefreshing.value = false
        }
    }
}