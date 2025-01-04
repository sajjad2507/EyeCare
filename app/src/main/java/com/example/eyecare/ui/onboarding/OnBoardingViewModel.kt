package com.example.eyecare.ui.onboarding

import androidx.lifecycle.ViewModel
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class OnBoardingViewModel : ViewModel() {
    private val _count = MutableStateFlow<Int>(0)
    val count : StateFlow<Int> get() = _count

    fun updateCounter() = vmScopeLaunch {
        _count.emit(_count.value + 1)
    }

    fun updateCounterRight() = vmScopeLaunch {
        _count.emit((_count.value - 1).coerceAtLeast(0))
    }
}