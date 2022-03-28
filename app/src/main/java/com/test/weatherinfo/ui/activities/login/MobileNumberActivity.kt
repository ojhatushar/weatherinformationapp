package com.test.weatherinfo.ui.activities.login

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.test.weatherinfo.R
import com.test.weatherinfo.databinding.ActivityMobileNumberBinding
import com.test.weatherinfo.utils.ProgressUtils
import com.test.weatherinfo.utils.extensions.liveSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit

@AndroidEntryPoint
class MobileNumberActivity : AppCompatActivity() {

    // this stores the phone number of the user
    var number: String = ""


    // create instance of firebase auth
    lateinit var auth: FirebaseAuth

    // we will use this to match the sent otp from firebase
    lateinit var storedVerificationId: String
    lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var activity: Activity = this
    private lateinit var binding: ActivityMobileNumberBinding

    private val viewModel by viewModels<LoginOtpViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_mobile_number)

        binding.lifecycleOwner = this
        binding.loginViewModel = viewModel

        auth = FirebaseAuth.getInstance()

        setupSnackbar()
        observeShowProgress()
        navigate()


        // Callback function for Phone Auth
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // This method is called when the verification is completed
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                startActivity(Intent(activity, OtpActivity::class.java))
                finish()
                Log.d("GFG", "onVerificationCompleted Success")
            }

            // Called when verification is failed add log statement to see the exception
            override fun onVerificationFailed(e: FirebaseException) {
                Log.d("GFG", "onVerificationFailed  $e")
            }

            // On code is sent by the firebase this method is called
            // in here we start a new activity where user can enter the OTP
            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d("GFG", "onCodeSent: $verificationId")
                storedVerificationId = verificationId
                resendToken = token

                // Start a new activity using intent
                // also send the storedVerificationId using intent
                // we will use this id to send the otp back to firebase
                val intent = Intent(activity, OtpActivity::class.java)
                intent.putExtra("storedVerificationId", storedVerificationId)
                startActivity(intent)
                finish()
            }
        }

    }


    private fun navigate() {
        viewModel.data.observe(this) { event ->
            event.getContentIfNotHandled()?.let {
                number = "+91$it"
                sendVerificationCode(number)
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


    private fun sendVerificationCode(number: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(number) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callbacks) // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
        Log.d("GFG", "Auth started")
    }
}