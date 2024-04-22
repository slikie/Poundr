package com.github.poundr.ui.component

import android.webkit.JavascriptInterface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@Composable
fun RecaptchaWebView(
    modifier: Modifier = Modifier
) {
    val state = rememberWebViewState("https://captcha-prod.grindr.com/android.html")

    WebView(
        state = state,
        modifier = modifier,
        onCreated = {
            it.addJavascriptInterface(object {
                @JavascriptInterface
                fun verifyToken(token: String) {
                    // Handle the token
                }
            }, "Android")
            it.settings.javaScriptEnabled = true
        }
    )
}