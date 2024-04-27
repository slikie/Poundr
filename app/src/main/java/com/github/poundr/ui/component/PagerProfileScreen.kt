package com.github.poundr.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.poundr.vm.PagerProfileViewModel

@Composable
fun PagerProfileScreen(
    viewModel: PagerProfileViewModel = hiltViewModel(),
    initialProfile: Int
) {
    val profiles = viewModel.profiles.flow.collectAsLazyPagingItems()

    val pagerState = rememberPagerState(
        initialPage = initialProfile,
        pageCount = { profiles.itemCount }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val profile = profiles[page]!!
//        Profile(
//            photoMediaHashes = profile.photoMediaHashes,
//            displayName = profile.displayName,
//            age = profile.age,
//            distance = profile.distance,
//            aboutMe = profile.aboutMe
//        )
    }
}