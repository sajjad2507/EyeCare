package com.example.eyecare.ui.utils

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object MethodUtils {
    fun setLanguage(context: Activity) {
        val sharedPref = context.getSharedPreferences(
            "appSharedPref",
            Context.MODE_PRIVATE
        )
        val lang = sharedPref.getString("selectedLanguageCode", "en")

        val locale = Locale(lang!!)
        Locale.setDefault(locale)

        val configuration = Configuration()
        configuration.setLocale(locale)
        context.resources.updateConfiguration(configuration, context.resources.displayMetrics)
    }
}