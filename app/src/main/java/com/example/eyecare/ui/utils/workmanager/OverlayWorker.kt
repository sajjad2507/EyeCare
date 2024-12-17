package com.example.eyecare.ui.utils.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import com.example.eyecare.ui.utils.services.OverlayService

class OverlayWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val action = inputData.getString("ACTION")

        return try {
            when (action) {
                "START" -> {
                    Log.d("Start Service","Start Service")
                    Log.d("Start Service",EasyPrefs.isFilterEnabled().toString())
                    EasyPrefs.setFilterEnabled(true)
                    OverlayService.start(applicationContext)
                }
                "STOP" -> {
                    Log.d("Stop Service","Stop Service")
                    EasyPrefs.setFilterEnabled(false)
                    OverlayService.stop(applicationContext)
                }
            }
            Result.success()
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure()
        }
    }
}