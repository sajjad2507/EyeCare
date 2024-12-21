package com.example.eyecare.ui.utils.roomdb.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.eyecare.ui.utils.Utils.vmScopeLaunch
import com.example.eyecare.ui.utils.roomdb.model.RemindersModel
import com.example.eyecare.ui.utils.roomdb.repository.ReminderRepository

class ReminderViewModel(private val repository: ReminderRepository) : ViewModel() {

    val allReminders: LiveData<List<RemindersModel>> = repository.allReminder

    fun insert(reminder: RemindersModel) = vmScopeLaunch {
        repository.insert(reminder)
    }

    fun update(reminder: RemindersModel) = vmScopeLaunch {
        repository.update(reminder)
    }

    fun delete(reminder: RemindersModel) = vmScopeLaunch {
        repository.delete(reminder)
    }

    class ReminderViewModelFactory(private val repository: ReminderRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ReminderViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ReminderViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}