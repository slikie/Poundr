package com.github.poundr.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.messaging.FirebaseMessaging
import kotlin.coroutines.resume

@Composable
fun HomeScreen(

) {
    LaunchedEffect(Unit) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("HomeScreen", "Firebase token: ${it.result}")
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Home",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }
}