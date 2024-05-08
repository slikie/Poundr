package com.github.poundr.ui.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.github.poundr.vm.PagerProfileViewModel

private const val TAG = "PagerProfileScreen"

@Composable
fun PagerProfileScreen(
    viewModel: PagerProfileViewModel = hiltViewModel(),
) {
    val profiles = viewModel.profiles.flow.collectAsLazyPagingItems()
    val initialProfilePage = remember {
        derivedStateOf {
            var initialProfilePage = 0
            for (i in 0 until profiles.itemCount) {
                if (profiles[i]?.profileId == viewModel.initialProfileId) {
                    initialProfilePage = i
                    break
                }
            }
            initialProfilePage
        }
    }.value

    val pagerState = rememberPagerState(
        initialPage = initialProfilePage,
        pageCount = { profiles.itemCount }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val profile = profiles[page]
        if (profile != null) {
//            Profile(
//                photoMediaHashes = profile.photoMediaHashes,
//                displayName = profile.displayName,
//                age = profile.age,
//                distance = profile.distance,
//                aboutMe = profile.aboutMe
//            )
        }
    }
}