package com.github.poundr.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.poundr.R
import com.github.poundr.vm.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val email = viewModel.email.collectAsState().value
    val password = viewModel.password.collectAsState().value
    val error = viewModel.error.collectAsState().value

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarHostState, error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.clearError()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            val layoutDirection = LocalLayoutDirection.current
            val defaultPadding = ScaffoldDefaults.contentWindowInsets.asPaddingValues()
            val padding = WindowInsets.ime.asPaddingValues()
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(
                    start = padding.calculateStartPadding(layoutDirection),
                    top = padding.calculateTopPadding(),
                    end = padding.calculateEndPadding(layoutDirection),
                    bottom = (padding.calculateBottomPadding() - defaultPadding.calculateBottomPadding()).coerceAtLeast(0.dp)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Image(
                painter = painterResource(R.drawable.poundr_logo),
                contentDescription = "Poundr logo"
            )

            Spacer(Modifier.height(24.dp))

            TextField(
                value = email,
                onValueChange = viewModel::setEmail,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Email")
                },
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            var hidePassword by remember { mutableStateOf(true) }
            TextField(
                value = password,
                onValueChange = viewModel::setPassword,
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text("Password")
                },
                trailingIcon = {
                    val icon = if (hidePassword) Icons.Default.Visibility else Icons.Default.VisibilityOff
                    val description = if (hidePassword) "Show password" else "Hide password"
                    IconButton(onClick = { hidePassword = !hidePassword }) {
                        Icon(
                            imageVector = icon,
                            contentDescription = description
                        )
                    }
                },
                visualTransformation = if (hidePassword) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = true
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isNotEmpty() && password.isNotEmpty()) {
                        viewModel.login()
                    } else if (email.isEmpty()) {
                        scope.launch { snackbarHostState.showSnackbar("Email is required") }
                    } else {
                        scope.launch { snackbarHostState.showSnackbar("Password is required") }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Forgot password?")
            }

            Spacer(Modifier.height(8.dp))

            TextButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign up")
            }

            Spacer(Modifier.weight(1f))

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login with Google")
            }

            Spacer(Modifier.height(16.dp))

            OutlinedButton(
                onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login with Facebook")
            }

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Poundr will never post to your social media accounts",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodySmall
            )

            Spacer(Modifier.height(16.dp))
        }
    }
}