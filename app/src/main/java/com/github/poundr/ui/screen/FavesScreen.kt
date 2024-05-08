package com.github.poundr.ui.screen

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.github.poundr.ui.none

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavesScreen(

) {
    var showFilterDialog by remember { mutableStateOf(false) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text("Faves")
                },
                actions = {
                    IconButton(onClick = { showFilterDialog = true }) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Filter"
                        )
                    }
                },
                windowInsets = WindowInsets.none
            )
        },
        contentWindowInsets = WindowInsets.none
    ) { innerPadding ->
//        val profiles = viewModel.profiles.flow.collectAsLazyPagingItems()
//        val isRefreshing = profiles.loadState.refresh == LoadState.Loading
//
//        PullRefreshBox(
//            refreshing = isRefreshing,
//            onRefresh = profiles::refresh,
//            modifier = Modifier.padding(innerPadding).fillMaxSize()
//        ) {
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(3),
//                modifier = Modifier.fillMaxSize(),
//                verticalArrangement = Arrangement.spacedBy(1.dp),
//                horizontalArrangement = Arrangement.spacedBy(1.dp)
//            ) {
//                items(
//                    count = profiles.itemCount,
//                    key = profiles.itemKey { it.profileId }
//                ) { index ->
//                    val profile = profiles[index]
//                    if (profile != null) {
//                        GridProfile(
//                            modifier = Modifier.fillMaxWidth().animateItem(),
//                            imageId = profile.avatarId,
//                            name = profile.name,
//                            onClick = { onBrowseProfile(profile.profileId) }
//                        )
//                    }
//                }
//            }
//        }
    }

    if (showFilterDialog) {
        // TODO: Open filter dialog
    }
}