package com.example.eyecare.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeFragmentViewModel : ViewModel() {

    private val _dimProgressFlow = MutableStateFlow<Int>(EasyPrefs.getDimLevel())
    val dimProgressFlow: StateFlow<Int> get() = _dimProgressFlow

    private val _intensityProgressFlow = MutableStateFlow<Int>(EasyPrefs.getIntensity())
    val intensityProgressFlow: StateFlow<Int> get() = _intensityProgressFlow

    private val _isFilterEnable = MutableStateFlow<Boolean>(EasyPrefs.isFilterEnabled())
    val isFilterEnable: StateFlow<Boolean> get() = _isFilterEnable

    private val _tempValueFlow = MutableStateFlow<String>(EasyPrefs.colorTemperature())
    val tempValueFlow : StateFlow<String> get() = _tempValueFlow

    fun setDimLevel(progress: Int) = vmScopeLaunch {
        _dimProgressFlow.emit(progress)
        _isFilterEnable.emit(true)
    }

    fun setIntensityLevel(progress: Int) = vmScopeLaunch {
        _intensityProgressFlow.emit(progress)
        _isFilterEnable.emit(true)
    }

    fun setTemperature() = vmScopeLaunch {
        _tempValueFlow.emit(EasyPrefs.colorTemperature())
    }
    fun setUpSwitch(checked: Boolean)= vmScopeLaunch {
        _isFilterEnable.emit(checked)
    }
    fun setUpFilter() = vmScopeLaunch {
        Log.d("Filter",EasyPrefs.isFilterEnabled().toString())
        _isFilterEnable.emit(EasyPrefs.isFilterEnabled())
    }

}