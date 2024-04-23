package com.github.poundr.ui.screen

import android.Manifest
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.poundr.model.ServerDrivenCascadeApiItem
import com.github.poundr.ui.component.GridProfile
import com.github.poundr.vm.BrowseViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun BrowseScreen(
    viewModel: BrowseViewModel = hiltViewModel(),
    onBrowseProfile: (Int) -> Unit
) {
    val coarseLocationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    if (coarseLocationPermissionState.status.isGranted) {
        LaunchedEffect(viewModel) {
            viewModel.refresh()
        }

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Text("Browse")
                    },
                    actions = {
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Default.AccountCircle,
                                contentDescription = "Profile"
                            )
                        }
                    }
                )
            }
        ) { innerPadding ->
            val refreshing = viewModel.refreshing.collectAsState().value
            val profiles = viewModel.profiles.collectAsState().value

            val pullRefreshState = rememberPullRefreshState(
                refreshing = refreshing,
                onRefresh = viewModel::refresh
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .pullRefresh(pullRefreshState)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(3),
                    modifier = Modifier.fillMaxSize(),
                ) {
                    itemsIndexed(profiles) { index, profile ->
                        when (profile) {
                            is ServerDrivenCascadeApiItem.Profile -> {
                                GridProfile(
                                    modifier = Modifier.fillMaxWidth(),
                                    imageId = profile.data.photoMediaHashes?.firstOrNull(),
                                    name = profile.data.displayName ?: "",
                                    onClick = { onBrowseProfile(index) }
                                )
                            }
                            is ServerDrivenCascadeApiItem.PartialProfile -> {
                                GridProfile(
                                    modifier = Modifier.fillMaxWidth(),
                                    imageId = profile.data.photoMediaHashes?.firstOrNull(),
                                    name = profile.data.displayName ?: "",
                                    onClick = { onBrowseProfile(index) }
                                )
                            }
                        }
                    }
                }

                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    modifier = Modifier.align(Alignment.TopCenter)
                )
            }
        }
    } else {
        Text("Missing location permission") // This should be unreachable
    }
}