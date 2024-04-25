package com.github.poundr.vm

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

private const val TAG = "ConversationViewModel"

@HiltViewModel
class ConversationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val conversationId: String? = savedStateHandle["conversationId"]

    init {
        Log.d(TAG, "init: Conversation ID: $conversationId")
    }
}