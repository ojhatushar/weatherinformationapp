package com.test.weatherinfo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.test.weatherinfo.R
import com.test.weatherinfo.ui.activities.login.MobileNumberActivity

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MobileNumberActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000) // 2000 is the delayed time in milliseconds.

    }
}