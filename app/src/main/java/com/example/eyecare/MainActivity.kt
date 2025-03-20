package com.example.eyecare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.eyecare.ui.utils.MethodUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MethodUtils.setLanguage(this)
        setContentView(R.layout.activity_main)
    }
}