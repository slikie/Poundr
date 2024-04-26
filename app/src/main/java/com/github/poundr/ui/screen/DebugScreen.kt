package com.github.poundr.ui.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.poundr.vm.DebugViewModel
import kotlinx.coroutines.launch

@Composable
fun DebugScreen(
    viewModel: DebugViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text("Debug Screen")

            Spacer(Modifier.weight(1f))

            Button(
                onClick = {
                    scope.launch {
                        viewModel.sendFcmToken()
                        Toast.makeText(context, "Sent FCM token to server", Toast.LENGTH_SHORT).show()
                    }
                },
            ) {
                Text("Send current FCM token to server")
            }

            Spacer(Modifier.weight(1f))
        }
    }
}