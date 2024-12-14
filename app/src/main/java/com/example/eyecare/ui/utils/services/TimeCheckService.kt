package com.example.eyecare.ui.utils.services

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeCheckService : Service() {

    companion object {
        @Synchronized
        fun stop(context: Context) {
            EasyPrefs.setPauseEnable(false)
            EasyPrefs.setFilterEnabled(true)
            EasyPrefs.setSeconds(60)
            val intent = Intent(context, TimeCheckService::class.java)
            context.stopService(intent)
        }
        @Synchronized
        fun start(context: Context){
            val serviceIntent = Intent(context, TimeCheckService::class.java)
            context.startService(serviceIntent)
        }
    }

    private val timeHandler = Handler(Looper.getMainLooper())
    private val timeRunnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            checkTime()
            val seconds = EasyPrefs.getSeconds()-1
            EasyPrefs.setSeconds(seconds)
            timeHandler.postDelayed(this, 1000) // Check time every second
        }
    }

    private lateinit var startTime: String
    private lateinit var endTime: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        initializeTimes() // Initialize start and end times
        timeHandler.post(timeRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initializeTimes() {
        // Get current time and calculate start and end times
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")

        startTime = currentTime.format(formatter)
        endTime = currentTime.plusMinutes(1).format(formatter)

        Log.d("Time Initialization", "Start Time: $startTime, End Time: $endTime")
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun checkTime() {
        Log.d("Check Time", "Checking current time")
        val currentTime = getCurrentTime()
        when {
            currentTime == startTime -> {
                Log.d("Check Time", "Start Time Reached")
                OverlayService.stop(applicationContext)
                EasyPrefs.setFilterEnabled(false)
                EasyPrefs.setPauseEnable(true)
            }
            currentTime == endTime -> {
                Log.d("Check Time", "End Time Reached")
                OverlayService.start(applicationContext)
                EasyPrefs.setFilterEnabled(true)
                EasyPrefs.setPauseEnable(false)
                stopService()
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun stopService() {
        timeHandler.removeCallbacks(timeRunnable)
        stopSelf()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTime(): String {
        val currentTime = LocalTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        return currentTime.format(formatter)
    }
}