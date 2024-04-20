package com.github.poundr.ui.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChatBubble
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.poundr.vm.MainViewModel

private enum class MainRoute(
    val route: String,
    val icon: ImageVector,
    val title: String
) {
    BROWSE("browse", Icons.Default.GridOn, "Browse"),
    MESSAGES("messages", Icons.Default.ChatBubble, "Messages"),
    TAPS("taps", Icons.Default.TouchApp, "Taps"),
    FAVES("faves", Icons.Default.Favorite, "Faves")
}

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            var selectedItem by remember { mutableIntStateOf(0) }
            NavigationBar {
                MainRoute.entries.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(imageVector = item.icon, contentDescription = null) },
                        label = { Text(item.title) },
                        selected = selectedItem == index,
                        onClick = { selectedItem = index }
                    )
                }
            }
        }
    ) { innerPadding ->
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = MainRoute.BROWSE.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(MainRoute.BROWSE.route) {
                BrowseScreen()
            }
            composable(MainRoute.MESSAGES.route) {
                MessagesScreen()
            }
            composable(MainRoute.TAPS.route) {
                TapsScreen()
            }
            composable(MainRoute.FAVES.route) {
                FavesScreen()
            }
        }
    }
}