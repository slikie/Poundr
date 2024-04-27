package com.github.poundr.vm

import androidx.lifecycle.ViewModel
import com.github.poundr.network.ServerDrivenCascadeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagerProfileViewModel @Inject constructor(
    serverDrivenCascadeRepository: ServerDrivenCascadeRepository,
) : ViewModel() {
    val profiles = serverDrivenCascadeRepository.getMessages(false)
}