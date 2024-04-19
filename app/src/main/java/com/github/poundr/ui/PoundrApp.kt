package com.github.poundr.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.github.poundr.ui.screen.LoginScreen
import com.github.poundr.ui.screen.MainScreen
import com.github.poundr.ui.theme.PoundrTheme
import com.github.poundr.vm.MainViewModel

@Composable
fun PoundrApp(
    mainViewModel: MainViewModel
) {
    val startDestination = mainViewModel.startDestination.collectAsState().value
    val navController = rememberNavController()

    PoundrTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = startDestination
            ) {
                composable("login") {
                    LoginScreen()
                }
                composable("main") {
                    MainScreen(mainViewModel)
                }
            }
        }
    }
}