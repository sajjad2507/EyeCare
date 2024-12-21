package com.example.eyecare.ui

import android.app.Application
import android.content.Context
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.roomdb.AppDatabase

class App : Application() {
    private val context: Context = this

    override fun onCreate() {
        super.onCreate()
        EasyPrefs.init(context)
//        AppDatabase.getDatabase(context)
    }
}