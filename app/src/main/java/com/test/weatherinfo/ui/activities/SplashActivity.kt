package com.test.weatherinfo.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

import com.test.weatherinfo.R
import com.test.weatherinfo.data.remote.Constants
import com.test.weatherinfo.di.PrefProvider
import com.test.weatherinfo.ui.activities.login.MobileNumberActivity

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    @Inject
    lateinit var prefProvider: PrefProvider

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler(Looper.getMainLooper()).postDelayed({
            if (prefProvider.getValueboolean(Constants.IS_LOGIN, false)) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MobileNumberActivity::class.java)
                startActivity(intent)
                finish()
            }

        }, 2000) // 2000 is the delayed time in milliseconds.

    }
}