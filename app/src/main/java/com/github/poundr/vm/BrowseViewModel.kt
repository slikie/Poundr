package com.github.poundr.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.poundr.network.ServerDrivenCascadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseViewModel @Inject constructor(
    private val serverDrivenCascadeRepository: ServerDrivenCascadeRepository,
) : ViewModel() {
    private val _refreshing = MutableStateFlow(false)
    val refreshing = _refreshing.asStateFlow()

    val profiles = serverDrivenCascadeRepository.profiles

    fun refresh() {
        viewModelScope.launch {
            _refreshing.value = true
            serverDrivenCascadeRepository.fetchData()
            _refreshing.value = false
        }
    }
}