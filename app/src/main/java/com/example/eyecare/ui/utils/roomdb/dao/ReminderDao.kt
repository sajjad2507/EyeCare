package com.example.eyecare.ui.utils.roomdb.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.eyecare.ui.utils.roomdb.model.RemindersModel

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReminder(reminder: RemindersModel)

    @Update
    suspend fun updateReminder(reminder: RemindersModel)

    @Delete
    suspend fun deleteReminder(reminder: RemindersModel)

    @Query("SELECT * FROM reminder_table")
    fun getAllReminders(): LiveData<List<RemindersModel>>
}