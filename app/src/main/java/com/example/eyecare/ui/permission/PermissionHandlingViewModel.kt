package com.example.eyecare.ui.permission

import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PermissionHandlingViewModel : ViewModel() {
    private val _androidVersion = MutableStateFlow<Int>(-1)
    val androidVersion : StateFlow<Int> get() = _androidVersion

    init {
        checkAndroidVersion()
    }

    private fun checkAndroidVersion() = vmScopeLaunch {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.R) {
            _androidVersion.emit(0)
        } else {
            _androidVersion.emit(1)
        }
    }
}