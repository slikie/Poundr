package com.github.poundr.ui.screen

import android.Manifest
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.github.poundr.ui.component.PagerProfile
import com.github.poundr.vm.MainViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState

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

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = if (coarseLocationPermissionState.status.isGranted) "home" else "location",
    ) {
        composable("home") {
            HomeScreen(
                onBrowseProfile = { profilePosition ->
                    navController.navigate("profile/$profilePosition")
                }
            )
        }
        composable("location") {
//            LocationScreen()
        }
        composable(
            route = "profile/{profileId}",
            arguments = listOf(navArgument("profileId") { type = NavType.IntType })
        ) { backStackEntry ->
            val profileId = backStackEntry.arguments?.getInt("profileId")
            LaunchedEffect(profileId) {
                Log.d("MainScreen", "Profile ID: $profileId")
            }
            if (profileId != null) {
                PagerProfile(
                    initialProfile = profileId
                )
            }
        }
    }
}