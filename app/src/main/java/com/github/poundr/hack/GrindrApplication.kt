package com.github.poundr.hack

import android.app.Application
import android.content.ComponentCallbacks
import android.content.ContextWrapper
import android.content.res.Configuration
import android.util.Log

private const val TAG = "GrindrApplication"

class GrindrApplication(
    private val application: Application
) : Application() {
    private val grindrContext = GrindrContext(application.applicationContext)

    init {
        ContextWrapper::class.java.getDeclaredField("mBase").apply {
            isAccessible = true
            set(this@GrindrApplication, grindrContext)
        }
    }

    override fun registerComponentCallbacks(callback: ComponentCallbacks?) {
        Log.d(TAG, "registerComponentCallbacks() called with: callback = $callback")
        application.registerComponentCallbacks(callback)
    }

    override fun unregisterComponentCallbacks(callback: ComponentCallbacks?) {
        Log.d(TAG, "unregisterComponentCallbacks() called with: callback = $callback")
        application.unregisterComponentCallbacks(callback)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        Log.d(TAG, "onConfigurationChanged() called with: newConfig = $newConfig")
        application.onConfigurationChanged(newConfig)
    }

    override fun onLowMemory() {
        Log.d(TAG, "onLowMemory() called")
        application.onLowMemory()
    }

    override fun onTrimMemory(level: Int) {
        Log.d(TAG, "onTrimMemory() called with: level = $level")
        application.onTrimMemory(level)
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate() called")
        application.onCreate()
    }

    override fun onTerminate() {
        Log.d(TAG, "onTerminate() called")
        application.onTerminate()
    }

    override fun registerActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        Log.d(TAG, "registerActivityLifecycleCallbacks() called with: callback = $callback")
        application.registerActivityLifecycleCallbacks(callback)
    }

    override fun unregisterActivityLifecycleCallbacks(callback: ActivityLifecycleCallbacks?) {
        Log.d(TAG, "unregisterActivityLifecycleCallbacks() called with: callback = $callback")
        application.unregisterActivityLifecycleCallbacks(callback)
    }

    override fun registerOnProvideAssistDataListener(callback: OnProvideAssistDataListener?) {
        Log.d(TAG, "registerOnProvideAssistDataListener() called with: callback = $callback")
        application.registerOnProvideAssistDataListener(callback)
    }

    override fun unregisterOnProvideAssistDataListener(callback: OnProvideAssistDataListener?) {
        Log.d(TAG, "unregisterOnProvideAssistDataListener() called with: callback = $callback")
        application.unregisterOnProvideAssistDataListener(callback)
    }
}