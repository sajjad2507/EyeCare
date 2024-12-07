package com.example.eyecare.ui.filterdashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.eyecare.R
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch

class FilterDashboardViewModel : ViewModel() {
    private val _items = MutableLiveData<MutableList<Int>>()
    val items : LiveData<MutableList<Int>> get() = _items

    init {
        setupList()
    }

    private fun setupList() = vmScopeLaunch {
       val itemsList = mutableListOf<Int>()
        itemsList.apply {
            add(R.drawable.ic_moon)
            add(R.drawable.ic_eye_care)
            add(R.drawable.ic_sunrise)
            add(R.drawable.ic_sundown)
            add(R.drawable.ic_candle)
            add(R.drawable.ic_trees_night)
            add(R.drawable.ic_tourch)
            add(R.drawable.ic_fire)
        }
        _items.postValue(itemsList)
    }
}