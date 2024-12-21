package com.example.eyecare.ui.utils.roomdb.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminder_table")
data class RemindersModel(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val reminderTitle: String,
    val reminderType: String,
    val reminderDesc: String
)
