package com.github.poundr.hack

import android.content.Context
import android.content.ContextWrapper
import com.github.poundr.App

class GrindrContext(context: Context) : ContextWrapper(context) {
    private val grindrPackageManager = GrindrPackageManager(context.packageManager, context.packageName)
    override fun getPackageManager() = grindrPackageManager
    override fun getApplicationContext() = null
    override fun getPackageName() = App.SPOOFED_PACKAGE_NAME
}