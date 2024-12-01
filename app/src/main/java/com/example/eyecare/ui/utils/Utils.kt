package com.example.eyecare.ui.utils

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController

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
}