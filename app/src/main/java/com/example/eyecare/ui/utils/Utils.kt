package com.example.eyecare.ui.utils

import android.accessibilityservice.AccessibilityService
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.accessibility.AccessibilityManager
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object Utils {
    // Extension Functions
    fun View.show() {
        this.visibility = View.VISIBLE
    }

    fun View.invisible() {
        this.visibility = View.INVISIBLE
    }

    fun View.hide() {
        this.visibility = View.GONE
    }

    fun Context.showToast(message: String, duration: Int = Toast.LENGTH_LONG) {
        Toast.makeText(this, message, duration).show()
    }

    fun Fragment.launchWhenStarted(action: suspend () -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            action.invoke()
        }
    }

    fun Fragment.onBackPressed(callback: () -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    callback.invoke()
                }
            })
    }

    fun Fragment.navigateBack() {
        findNavController().navigateUp()
    }
    fun TextView.setTextColorRes(@ColorRes colorRes: Int) {
        this.setTextColor(ContextCompat.getColor(context, colorRes))
    }

    fun Fragment.navigate(
        destination: Int,
        popBackStack: Boolean = true,
        popUpToInclusiveId: Int = destination, //should be the destination in current graph, or dashboardFragment
        bundle: Bundle? = null,
    ) {
        launchWhenStarted {
            try {
                if (popBackStack) {
                    findNavController().popBackStack(popUpToInclusiveId, true)
                }
                findNavController().navigate(destination, bundle)
            } catch (e: IllegalStateException) {
                e.printStackTrace()
            }
        }
    }
    fun View.setSingleClickListener(delayMillis: Long = 700, action: (View) -> Unit) {
        setOnClickListener {
            isClickable = false
            action.invoke(this)
            postDelayed({
                isClickable = true
            }, delayMillis)
        }
    }
    fun Context.hasOverlayPermission(): Boolean {
        return Settings.canDrawOverlays(this)
    }

    fun ViewModel.vmScopeLaunch(action: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch(Dispatchers.IO) { action() }
    }

    fun isAccessibilityServiceEnabled(context: Context, service: Class<out AccessibilityService>): Boolean {
        val am = context.getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        val enabledServices = am.getEnabledAccessibilityServiceList(1)

        for (enabledService in enabledServices) {
            val enabledServiceInfo = enabledService.resolveInfo.serviceInfo
            if (enabledServiceInfo.packageName == context.packageName && enabledServiceInfo.name == service.name) {
                return true
            }
        }

        return false
    }

    fun openAccessibility(context: Context) {
        val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        context.startActivity(intent)
    }

    fun checkAndroidVersion() : Boolean {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            return true
        } else {
            return false
        }
    }
}