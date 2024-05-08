package com.github.poundr.vm

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.github.poundr.persistence.PoundrDatabase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PagerProfileViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    poundrDatabase: PoundrDatabase
) : ViewModel() {
    val initialProfileId = savedStateHandle.get<Long>("initialProfileId")

    val profiles = Pager(
        config = PagingConfig(pageSize = 20),
        pagingSourceFactory = { poundrDatabase.cascadeDao().getCascadeItemsPagingSource() }
    )
}