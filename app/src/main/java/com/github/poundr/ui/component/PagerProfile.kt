package com.github.poundr.ui.component

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.poundr.network.model.ServerDrivenCascadeApiItem
import com.github.poundr.vm.BrowseViewModel

@Composable
fun PagerProfile(
    viewModel: BrowseViewModel = hiltViewModel(),
    initialProfile: Int
) {
    val profiles = viewModel.profiles.collectAsState().value

    LaunchedEffect(profiles) {
        Log.d("PagerProfile", "Initial profile: $initialProfile")
        Log.d("PagerProfile", "Profiles: ${profiles.size}")
    }

    val pagerState = rememberPagerState(
        initialPage = initialProfile,
        pageCount = { profiles.size }
    )

    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize()
    ) { page ->
        val profile = profiles[page]
        Log.d("PagerProfile", "Profile: $profile")
        when (profile) {
            is ServerDrivenCascadeApiItem.Profile -> Profile(profile.data)
            is ServerDrivenCascadeApiItem.PartialProfile -> Profile(profile.data)
            else -> Text("Unknown profile type")
        }
    }
}