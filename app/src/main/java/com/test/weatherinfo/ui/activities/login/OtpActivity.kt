package com.test.weatherinfo.ui.activities.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.test.weatherinfo.R
import com.test.weatherinfo.databinding.ActivityOtpBinding
import com.test.weatherinfo.ui.activities.MainActivity
import com.test.weatherinfo.utils.ProgressUtils
import com.test.weatherinfo.utils.extensions.liveSnackBar
import com.test.weatherinfo.utils.extensions.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat

@AndroidEntryPoint
class OtpActivity : AppCompatActivity() {

    private var storedVerificationId: String? = ""
    var activity: Activity = this
    private lateinit var binding: ActivityOtpBinding

    lateinit var auth: FirebaseAuth

    private lateinit var timer: CountDownTimer
    private val viewModel by viewModels<LoginOtpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_otp)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        auth = FirebaseAuth.getInstance()

        storedVerificationId = intent.getStringExtra("storedVerificationId")

        setupSnackbar()
        observeShowProgress()
        navigate()


        binding.layoutToolbar.tvToolbarTitle.text = getString(R.string.otp_screen)
        binding.layoutToolbar.ivBack.setOnClickListener {
            finish()
        }

        timer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                // Used for formatting digit to be in 2 digits only
                val f = DecimalFormat("00")
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60

                binding.tvTimer.text = f.format(min) + ":" + f.format(sec)
            }

            override fun onFinish() {
                binding.tvTimer.visibility = View.GONE
            }
        }
        timer.start()

    }

    private fun navigate() {

        viewModel.data.observe(this) { event ->
            event.getContentIfNotHandled()?.let { otp ->

                val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(
                    storedVerificationId.toString(), otp
                )
                signInWithPhoneAuthCredential(credential)
            }
        }

    }

    // verifies if the code matches sent by firebase
    // if success start the new activity in our case it is main Activity
    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    // Sign in failed, display a message and update the UI
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                        binding.root.showSnackbar("Invalid OTP", Snackbar.LENGTH_SHORT)
                    }
                }
            }
    }

    private fun observeShowProgress() {
        viewModel.showProgress.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    ProgressUtils.showProgressDialog(activity)
                } else {
                    ProgressUtils.dismissProgressDialog()
                }
            }
        }
    }

    private fun setupSnackbar() {

        binding.root.liveSnackBar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)


    }
}