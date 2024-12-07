package com.example.eyecare.ui.home

import androidx.lifecycle.ViewModel
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeFragmentViewModel : ViewModel() {

    private val _dimProgressFlow = MutableStateFlow<Int>(0)
    val dimProgressFlow: StateFlow<Int> get() = _dimProgressFlow

    private val _intensityProgressFlow = MutableStateFlow<Int>(0)
    val intensityProgressFlow: StateFlow<Int> get() = _intensityProgressFlow

    fun setDimLevel(progress: Int) = vmScopeLaunch {
        _dimProgressFlow.emit(progress)
    }

    fun setIntensityLevel(progress: Int) = vmScopeLaunch {
        _intensityProgressFlow.emit(progress)
    }

}