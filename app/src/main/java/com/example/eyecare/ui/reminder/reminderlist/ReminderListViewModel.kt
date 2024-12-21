package com.example.eyecare.ui.reminder.reminderlist

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow

class ReminderListViewModel : ViewModel() {
    val selectedTypePosition = MutableSharedFlow<Int>(0)
}