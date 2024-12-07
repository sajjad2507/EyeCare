package com.example.eyecare.ui

import android.app.Application
import android.content.Context
import android.content.ContextWrapper
import android.util.Log
import com.pixplicity.easyprefs.library.Prefs

class App : Application() {
    private val context: Context = this

    override fun onCreate() {
        super.onCreate()
        Log.d("App","App")
        Prefs.Builder()
            .setContext(context)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(context.packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }
}