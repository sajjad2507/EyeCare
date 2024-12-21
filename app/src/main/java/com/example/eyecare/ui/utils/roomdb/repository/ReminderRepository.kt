package com.example.eyecare.ui.utils.roomdb.repository

import androidx.lifecycle.LiveData
import com.example.eyecare.ui.utils.roomdb.dao.ReminderDao
import com.example.eyecare.ui.utils.roomdb.model.RemindersModel

class ReminderRepository(private val reminderDao: ReminderDao) {
    val allReminder: LiveData<List<RemindersModel>> = reminderDao.getAllReminders()
    suspend fun insert(reminder: RemindersModel) {
        reminderDao.insertReminder(reminder)
    }

    suspend fun update(reminder: RemindersModel) {
        reminderDao.updateReminder(reminder)
    }

    suspend fun delete(reminder: RemindersModel) {
        reminderDao.deleteReminder(reminder)
    }
}