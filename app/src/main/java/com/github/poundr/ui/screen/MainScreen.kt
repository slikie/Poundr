package com.github.poundr.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LifecycleResumeEffect
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.poundr.ui.component.PagerProfileScreen
import com.github.poundr.vm.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

sealed class MainScreenScreen(
    val route: String,
    val arguments: List<NamedNavArgument> = emptyList(),
) {
    data object Home : MainScreenScreen("home")
    data object Location : MainScreenScreen("location")
    data object Profile : MainScreenScreen(
        route = "profile/{initialProfileId}",
        arguments = listOf(
            navArgument("initialProfileId") { type = NavType.LongType }
        )
    )
    data object Conversation : MainScreenScreen(
        route = "conversation/{conversationId}",
        arguments = listOf(
            navArgument("conversationId") { type = NavType.StringType }
        )
    ) {
        fun createRoute(conversationId: String) = "conversation/$conversationId"
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
) {
    val shouldSendFcmToken = remember(viewModel) { viewModel.shouldSendFcmToken }
    if (shouldSendFcmToken) {
        LaunchedEffect(viewModel) {
            viewModel.sendFcmToken()
        }
    }

    val coarseLocationPermissionState = rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    @SuppressLint("MissingPermission")
    if (coarseLocationPermissionState.status.isGranted) {
        LaunchedEffect(viewModel) {
            viewModel.updateLocation()
        }
    } else {
        val locationPermissionsState = rememberMultiplePermissionsState(listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        ))

        LaunchedEffect(locationPermissionsState) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }

        Column {
            val textToShow = if (locationPermissionsState.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The camera is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Camera permission required for this feature to be available. " +
                        "Please grant the permission"
            }
            Text(textToShow)
            Button(onClick = { locationPermissionsState.launchMultiplePermissionRequest() }) {
                Text("Request permission")
            }
        }
    }

    LifecycleResumeEffect(Unit) {
        viewModel.connectWebSocket()
        onPauseOrDispose { viewModel.disconnectWebSocket() }
    }

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (coarseLocationPermissionState.status.isGranted) "home" else "location",
    ) {
        composable(
            route = MainScreenScreen.Home.route,
            arguments = MainScreenScreen.Home.arguments
        ) {
            HomeScreen(
                onBrowseProfile = { profileId ->
                    navController.navigate("profile/$profileId")
                },
                onConversationClick = { conversationId ->
                    navController.navigate("conversation/$conversationId")
                }
            )
        }

        composable(
            route = MainScreenScreen.Location.route,
            arguments = MainScreenScreen.Location.arguments
        ) {
//            LocationScreen()
        }

        composable(
            route = MainScreenScreen.Profile.route,
            arguments = MainScreenScreen.Profile.arguments
        ) { backStackEntry ->
            PagerProfileScreen()
        }

        composable(
            route = MainScreenScreen.Conversation.route,
            arguments = MainScreenScreen.Conversation.arguments
        ) {
            ConversationScreen(
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}