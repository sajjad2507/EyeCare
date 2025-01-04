package com.example.eyecare.ui.utils.preferences

import android.content.Context
import android.content.ContextWrapper
import com.bluelightfilter.prefs.SharedPreferenceBooleanLiveData
import com.bluelightfilter.prefs.SharedPreferenceIntLiveData
import com.bluelightfilter.prefs.SharedPreferenceStringLiveData
import com.example.eyecare.ui.utils.constants.Constants
import com.pixplicity.easyprefs.library.Prefs

object EasyPrefs {

    private const val KEY_FILTER_ENABLED = "KEY_FILTER_ENABLED"
    private const val KEY_COLOR_TEMPERATURE = "KEY_COLOR_TEMPERATURE"
    private const val KEY_INTENSITY = "KEY_INTENSITY"
    private const val KEY_INTENSITY_DEFAULT = 0
    private const val KEY_DIM_LEVEL = "KEY_DIM_LEVEL"
    private const val KEY_COLOR_VALUE = "KEY_COLOR_VALUE"
    private const val KEY_START_TIME = "KEY_START_TIME"
    private const val KEY_END_TIME = "KEY_END_TIME"
    private const val DEFAULT_START_TIME = "03:20 PM"
    private const val DEFAULT_END_TIME = "10:50 AM"
    private const val KEY_SWITCH_VALUE = "03:20 AM"
    private const val KEY_SHOW_HELP = "KEY_SHOW_HELP"
    private const val KEY_LIST_POSITION = "KEY_LIST_POSITION"
    private const val KEY_NOTIFICATION_ENABLED = "KEY_NOTIFICATION_ENABLED"
    private const val KEY_LIGHT_ENABLE = "KEY_LIGHT_ENABLE"
    private const val KEY_PAUSE_ENABLE = "KEY_PAUSE_ENABLE"
    private const val KEY_SETTING_ENABLE = "KEY_SETTING_ENABLE"
    private const val KEY_SECONDS_COUNT = "KEY_SECONDS_COUNT"
    private const val KEY_ONBOARDING_ENABLED = "KEY_ONBOARDING_ENABLED"


    fun init(context: Context) {
        Prefs.Builder()
            .setContext(context)
            .setMode(ContextWrapper.MODE_PRIVATE)
            .setPrefsName(context.packageName)
            .setUseDefaultSharedPreference(true)
            .build()
    }

    fun colorTemperature(): String {
        return Prefs.getString(KEY_COLOR_TEMPERATURE, "0K")
    }

    fun colorTemperatureLiveData(): SharedPreferenceStringLiveData {
        return SharedPreferenceStringLiveData(
            Prefs.getPreferences(),
            KEY_COLOR_TEMPERATURE, Constants.EYE_CARE_VALUE
        )
    }

    fun setColorTemperature(temperature: String) {
        Prefs.putString(KEY_COLOR_TEMPERATURE, temperature)
    }


    fun getStartTime(): String {
        return Prefs.getString(KEY_START_TIME, DEFAULT_START_TIME)
    }

    fun setStartTime(startTime: String) {
        Prefs.putString(KEY_START_TIME, startTime)
    }

    fun getEndTime(): String {
        return Prefs.getString(KEY_END_TIME, DEFAULT_END_TIME)
    }

    fun setEndTime(endTime: String) {
        Prefs.putString(KEY_END_TIME, endTime)
    }

    fun isFilterEnabled(): Boolean {
        return Prefs.getBoolean(KEY_FILTER_ENABLED, false)
    }

    fun isFilterDisabled(): Boolean {
        return isFilterEnabled().not()
    }

    fun setFilterEnabled(enabled: Boolean) {
        Prefs.putBoolean(KEY_FILTER_ENABLED, enabled)
    }

    fun getFilterSwitchLive(): SharedPreferenceBooleanLiveData {
        return SharedPreferenceBooleanLiveData(
            Prefs.getPreferences(),
            KEY_FILTER_ENABLED, false
        )
    }

    fun isNotificationEnabled(): Boolean {
        return Prefs.getBoolean(KEY_NOTIFICATION_ENABLED, false)
    }

    fun setNotificationEnabled(enabled: Boolean) {
        Prefs.putBoolean(KEY_NOTIFICATION_ENABLED, enabled)
    }

    fun getIntensity(): Int {
        return Prefs.getInt(KEY_INTENSITY, KEY_INTENSITY_DEFAULT)
    }

    fun getIntensityLive(): SharedPreferenceIntLiveData {
        return SharedPreferenceIntLiveData(
            Prefs.getPreferences(),
            KEY_INTENSITY,
            KEY_INTENSITY_DEFAULT
        )
    }

    fun setIntensity(intensity: Int) {
        Prefs.putInt(KEY_INTENSITY, intensity)
    }

    fun getDimLevel(): Int {
        return Prefs.getInt(KEY_DIM_LEVEL, 0)
    }

    fun setDimLevel(dim: Int) {
        Prefs.putInt(KEY_DIM_LEVEL, dim)
    }

    fun getDimLevelLive(): SharedPreferenceIntLiveData {
        return SharedPreferenceIntLiveData(Prefs.getPreferences(), KEY_DIM_LEVEL, 0)
    }

    fun getSeconds(): Int {
        return Prefs.getInt(KEY_SECONDS_COUNT, 60)
    }

    fun setSeconds(seconds: Int) {
        Prefs.putInt(KEY_SECONDS_COUNT, seconds)
    }

    fun getSecondsLive(): SharedPreferenceIntLiveData {
        return SharedPreferenceIntLiveData(Prefs.getPreferences(), KEY_SECONDS_COUNT, 60)
    }

    fun getSwitchTimer(): Boolean {
        return Prefs.getBoolean(KEY_COLOR_VALUE, false)
    }

    fun setSwitchTimer(timer: Boolean) {
        Prefs.putBoolean(KEY_COLOR_VALUE, timer)
    }

    fun showHelp(): Boolean {
        return Prefs.getBoolean(KEY_SHOW_HELP, true)
    }

    fun disableHelp() {
        Prefs.putBoolean(KEY_SHOW_HELP, false)
    }

    fun getItem(): Int {
        return Prefs.getInt(KEY_LIST_POSITION)
    }

    fun setItem(poistion: Int) {
        Prefs.putInt(KEY_LIST_POSITION, poistion)
    }

    fun setLightEnable(enabled: Boolean) {
        Prefs.putBoolean(KEY_LIGHT_ENABLE, enabled)
    }

    fun isLightEnable(): Boolean {
        return Prefs.getBoolean(KEY_LIGHT_ENABLE, false)
    }

    fun setPauseEnable(enabled: Boolean) {
        Prefs.putBoolean(KEY_PAUSE_ENABLE, enabled)
    }
    fun isPauseEnable(): Boolean {
        return Prefs.getBoolean(KEY_PAUSE_ENABLE, false)
    }
    fun setSettingEnable(enabled: Boolean) {
        Prefs.putBoolean(KEY_PAUSE_ENABLE, enabled)
    }
    fun isSettingEnable(): Boolean {
        return Prefs.getBoolean(KEY_SETTING_ENABLE, false)
    }

    fun setOnBoardingEnable(enabled: Boolean) {
        Prefs.putBoolean(KEY_ONBOARDING_ENABLED, enabled)
    }

    fun isOnBoardingEnable(): Boolean {
        return Prefs.getBoolean(KEY_ONBOARDING_ENABLED, true)
    }
}