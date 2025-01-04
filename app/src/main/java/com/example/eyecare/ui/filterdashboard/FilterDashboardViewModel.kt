package com.example.eyecare.ui.filterdashboard

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FilterDashboardViewModel : ViewModel() {
    private val _dimProgressFlow = MutableStateFlow<Int>(EasyPrefs.getDimLevel())
    val dimProgressFlow: StateFlow<Int> get() = _dimProgressFlow

    private val _intensityProgressFlow = MutableStateFlow<Int>(EasyPrefs.getIntensity())
    val intensityProgressFlow: StateFlow<Int> get() = _intensityProgressFlow

    private val _isFilterEnable = MutableStateFlow<Boolean>(EasyPrefs.isFilterEnabled())
    val isFilterEnable: StateFlow<Boolean> get() = _isFilterEnable

    private val _tempValueFlow = MutableStateFlow<String>(EasyPrefs.colorTemperature())
    val tempValueFlow : StateFlow<String> get() = _tempValueFlow

    private val _timeRemaining = MutableLiveData<Long>()
    val timeRemaining: LiveData<Long> get() = _timeRemaining

    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    init {
        setUpFilter()
        if(EasyPrefs.getSeconds() < 60){
            val timeMillis = EasyPrefs.getSeconds() * 1000
            startTimer(timeMillis.toLong())
        }
    }
    fun setDimLevel(progress: Int) = vmScopeLaunch {
        Log.d("Dim Change","Dim Change")
        _dimProgressFlow.emit(progress)
        _isFilterEnable.emit(true)
    }

    fun setIntensityLevel(progress: Int) = vmScopeLaunch {
        Log.d("Intensity Change","Intensity Change")
        _intensityProgressFlow.emit(progress)
        _isFilterEnable.emit(true)
    }

    fun setUpSwitch(checked: Boolean)= vmScopeLaunch {
        _isFilterEnable.emit(checked)
    }
    fun setUpFilter() = vmScopeLaunch {
        Log.d("Filter",EasyPrefs.isFilterEnabled().toString())
        _isFilterEnable.emit(EasyPrefs.isFilterEnabled())
    }
    fun setUpPause(checked: Boolean) = vmScopeLaunch {
        _isFilterEnable.emit(checked)
    }

    fun setUpTemperature(eyeCareValue: String) = vmScopeLaunch {
        _tempValueFlow.emit(eyeCareValue)
    }

    fun startTimer(timeInMillis: Long) {
        if (isTimerRunning) return

        countDownTimer = object : CountDownTimer(timeInMillis, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _timeRemaining.value = millisUntilFinished / 1000
            }

            override fun onFinish() {
                _timeRemaining.value = 0
                isTimerRunning = false
            }
        }.start()

        isTimerRunning = true
    }

    fun stopTimer() {
        countDownTimer?.cancel()
        isTimerRunning = false
        _timeRemaining.value = 0
    }

    fun isTimerActive(): Boolean {
        return isTimerRunning
    }
}