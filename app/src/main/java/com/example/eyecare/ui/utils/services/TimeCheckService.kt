package com.example.eyecare.ui.utils.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.eyecare.R
import com.example.eyecare.ui.utils.preferences.EasyPrefs
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TimeCheckService : Service() {

    companion object {
        @Synchronized
        fun stop(context: Context) {
            val intent = Intent(context, TimeCheckService::class.java)
            context.stopService(intent)
            EasyPrefs.setSeconds(60)
        }
    }

    private val timeHandler = Handler(Looper.getMainLooper())
    private val timeRunnable = object : Runnable {
        @RequiresApi(Build.VERSION_CODES.O)
        override fun run() {
            checkTime()
            EasyPrefs.setSeconds(EasyPrefs.getSeconds()-1)
            timeHandler.postDelayed(this, 1000)
        }
    }


    private val CHANNEL_ID = "TimeCheckServiceChannel"
    private lateinit var startTime: String
    private lateinit var endTime: String
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()

        timeHandler.post(timeRunnable)
        initializeTimes()

        Log.d("Time Check Service ", "Time Check Service")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel()
        }

        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle("Time Check Service")
            .setContentText("Checking time...")
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's notification icon
            .build()

        startForeground(1, notification)
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
                EasyPrefs.setFilterEnabled(true)
                OverlayService.start(applicationContext)
                EasyPrefs.setPauseEnable(false)
                stopService()
            }
        }
    }


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val name = "Time Check Service Channel"
        val descriptionText = "Channel for Time Check Service"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }

        val notificationManager: NotificationManager =
            getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
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

















//class TimeCheckService : Service() {
//
//    companion object {
//        @Synchronized
//        fun stop(context: Context) {
//            EasyPrefs.setPauseEnable(false)
//            EasyPrefs.setFilterEnabled(true)
//            EasyPrefs.setSeconds(60)
//            val intent = Intent(context, TimeCheckService::class.java)
//            context.stopService(intent)
//        }
//        @Synchronized
//        fun start(context: Context){
//            val serviceIntent = Intent(context, TimeCheckService::class.java)
//            context.startService(serviceIntent)
//        }
//    }
//    private val CHANNEL_ID = "TimeCheckServiceChannel"
//
//    private val timeHandler = Handler(Looper.getMainLooper())
//    private val timeRunnable = object : Runnable {
//        @RequiresApi(Build.VERSION_CODES.O)
//        override fun run() {
//            checkTime()
//            val seconds = EasyPrefs.getSeconds()-1
//            EasyPrefs.setSeconds(seconds)
//            timeHandler.postDelayed(this, 1000) // Check time every second
//        }
//    }
//
//    private lateinit var startTime: String
//    private lateinit var endTime: String
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreate() {
//        super.onCreate()
//
//        initializeTimes() // Initialize start and end times
//        timeHandler.post(timeRunnable)
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            createNotificationChannel()
//        }
//
//        val notification: Notification = Notification.Builder(this, CHANNEL_ID)
//            .setContentTitle("Time Check Service")
//            .setContentText("Checking time...")
//            .setSmallIcon(R.drawable.ic_launcher_foreground) // Replace with your app's notification icon
//            .build()
//
//        startForeground(1, notification)
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        stopService()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun initializeTimes() {
//        // Get current time and calculate start and end times
//        val currentTime = LocalTime.now()
//        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//
//        startTime = currentTime.format(formatter)
//        endTime = currentTime.plusMinutes(1).format(formatter)
//
//        Log.d("Time Initialization", "Start Time: $startTime, End Time: $endTime")
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun checkTime() {
//        Log.d("Check Time", "Checking current time")
//        val currentTime = getCurrentTime()
//        when {
//            currentTime == startTime -> {
//                Log.d("Check Time", "Start Time Reached")
//                OverlayService.stop(applicationContext)
//                EasyPrefs.setFilterEnabled(false)
//                EasyPrefs.setPauseEnable(true)
//            }
//            currentTime == endTime -> {
//                Log.d("Check Time", "End Time Reached")
//                val intent = Intent(applicationContext, OverlayService::class.java)
//                applicationContext.startForegroundService(intent)
//                EasyPrefs.setFilterEnabled(true)
//                EasyPrefs.setPauseEnable(false)
//                stopService()
//            }
//        }
//    }
//
//    override fun onBind(intent: Intent?): IBinder? {
//        return null
//    }
//
//    private fun stopService() {
//        timeHandler.removeCallbacks(timeRunnable)
//        stopSelf()
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun createNotificationChannel() {
//        val name = "Time Check Service Channel"
//        val descriptionText = "Channel for Time Check Service"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//
//        val notificationManager: NotificationManager =
//            getSystemService(NotificationManager::class.java)
//        notificationManager.createNotificationChannel(channel)
//    }
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun getCurrentTime(): String {
//        val currentTime = LocalTime.now()
//        val formatter = DateTimeFormatter.ofPattern("HH:mm:ss")
//        return currentTime.format(formatter)
//    }
//}