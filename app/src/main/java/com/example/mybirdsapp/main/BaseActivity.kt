package com.example.mybirdsapp.main

import android.content.Context
import androidx.activity.ComponentActivity

open class BaseActivity : ComponentActivity() {
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase?.let { adjustFontScale(it) })
    }

    private fun adjustFontScale(context: Context): Context {
        val configuration = context.resources.configuration
        if (configuration.fontScale != 1.0f) {
            configuration.fontScale = 1.0f
            val metrics = context.resources.displayMetrics
            val res = context.createConfigurationContext(configuration)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            return res
        }
        return context
    }
}