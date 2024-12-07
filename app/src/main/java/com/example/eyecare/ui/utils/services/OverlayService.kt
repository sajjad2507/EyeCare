package com.example.eyecare.ui.utils.services

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.view.Gravity
import android.view.WindowManager
import android.view.accessibility.AccessibilityEvent
import androidx.lifecycle.Observer
import com.example.eyecare.ui.utils.Utils.hasOverlayPermission
import com.example.eyecare.ui.utils.constants.Constants.BULB_VALUE
import com.example.eyecare.ui.utils.constants.Constants.CANDLE_VALUE
import com.example.eyecare.ui.utils.constants.Constants.DEFAULT_VALUE
import com.example.eyecare.ui.utils.constants.Constants.DOWN_VALUE
import com.example.eyecare.ui.utils.constants.Constants.FLUORESCENT_VALUE
import com.example.eyecare.ui.utils.constants.Constants.NIGHT_LIGHT_VALUE
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import kotlin.concurrent.Volatile

class OverlayService : AccessibilityService() {

    companion object {
        @Volatile
        private var isRunning: Boolean = false

        @JvmStatic
        fun start(context: Context) {
            val intent = Intent(context, OverlayService::class.java)
            context.startService(intent)
        }

        @Synchronized
        fun stop(context: Context) {
            val intent = Intent(context, OverlayService::class.java)
            context.stopService(intent)
        }
    }

    private val dimLevelLiveData = EasyPrefs.getDimLevelLive()
    private val intensityLiveData = EasyPrefs.getIntensityLive()
    private val filterSwitchLiveData = EasyPrefs.getFilterSwitchLive()
    private val colorTemperateLiveData = EasyPrefs.colorTemperatureLiveData()

    private val dimLevelObserver = Observer<Int> { value -> updateScreenDim(value) }

    private val intensityObserver = Observer<Int> { value ->
        val temp = EasyPrefs.colorTemperature()
        updateOverlayColor(value, temp)
    }

    private val colorTemperateObserver = Observer<String> { value ->
        val intensity = EasyPrefs.getIntensity()
        updateOverlayColor(intensity, value)
    }

    private val filterSwitchObserver = Observer<Boolean> { value ->
        if (value) {
            showOverlay()
        } else {
            removeOverlay()
        }
    }

    private val windowManager by lazy {
        getSystemService(WINDOW_SERVICE) as WindowManager
    }

    private val overlayView by lazy { OverlayView(this) }

    override fun onCreate() {
        super.onCreate()
        isRunning = true

        addObservers()
    }

    override fun onServiceConnected() {
        super.onServiceConnected()
    }

    private fun addObservers() {
        dimLevelLiveData.observeForever(dimLevelObserver)
        intensityLiveData.observeForever(intensityObserver)
        filterSwitchLiveData.observeForever(filterSwitchObserver)
        colorTemperateLiveData.observeForever(colorTemperateObserver)
    }

    private fun showOverlay() {
        if (hasOverlayPermission().not()) return
        windowManager.addView(overlayView, getLayoutParams())
    }

    private fun getLayoutParams(): WindowManager.LayoutParams {
        val displaySize = Point()
        windowManager.defaultDisplay.getRealSize(displaySize)
        return WindowManager.LayoutParams().apply {
            x = 0
            y = 0
            width = displaySize.x
            height = displaySize.y
            gravity = Gravity.START or Gravity.TOP
            type = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
            }
            alpha = 0.80f // Add this
            flags = getFlags()
            format = PixelFormat.TRANSLUCENT
        }
    }

    private fun getFlags(): Int {
        return WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
    }

    private fun updateOverlayColor(intensity: Int, temp: String) {
        if (overlayView.isAttachedToWindow.not()) return
        val alpha = (intensity / 100f) * 255
        val color = when (temp) {
            DEFAULT_VALUE -> Color.argb(alpha.toInt(), 125, 0, 0)
            NIGHT_LIGHT_VALUE -> Color.argb(alpha.toInt(), 61, 79, 102)
            CANDLE_VALUE -> Color.argb(alpha.toInt(), 255, 147, 0)
            DOWN_VALUE -> Color.argb(alpha.toInt(), 255, 223, 0)
            BULB_VALUE -> Color.argb(alpha.toInt(), 255, 197, 143)
            FLUORESCENT_VALUE -> Color.argb(alpha.toInt(), 0, 50, 176)
            else -> Color.TRANSPARENT
        }
        overlayView.setFilterColor(color)
    }

    private fun updateScreenDim(dimLevel: Int) {
        if (overlayView.isAttachedToWindow.not()) return
        val alpha = (dimLevel / 100f) * 255
        overlayView.setDimColor(Color.argb(alpha.toInt(), 0, 0, 0))
    }
    override fun onDestroy() {
        super.onDestroy()
        isRunning = false

        removeOverlay()
        removeObservers()
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun removeObservers() {
        dimLevelLiveData.removeObserver(dimLevelObserver)
        intensityLiveData.removeObserver(intensityObserver)
        colorTemperateLiveData.removeObserver(colorTemperateObserver)
        filterSwitchLiveData.removeObserver(filterSwitchObserver)
    }

    private fun removeOverlay() {
        if (overlayView.isAttachedToWindow) {
            windowManager.removeView(overlayView)
        }
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {}

    override fun onInterrupt() {}
}
