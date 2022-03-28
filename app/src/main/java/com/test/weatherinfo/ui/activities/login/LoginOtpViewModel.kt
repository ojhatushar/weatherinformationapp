package com.test.weatherinfo.ui.activities.login

import android.text.TextUtils
import androidx.lifecycle.*
import com.example.financialinvestment.data.repositories.WeatherInfoRepository

import com.test.weatherinfo.R
import com.test.weatherinfo.data.model.requestModel.LoginOtpRequestModel
import com.test.weatherinfo.data.remote.Constants.IS_LOGIN
import com.test.weatherinfo.di.PrefProvider
import com.test.weatherinfo.utils.Event
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginOtpViewModel @Inject constructor(
    private val prefProvider: PrefProvider
) :
    ViewModel() {


    val loginDetails = MutableLiveData(LoginOtpRequestModel())

    private val _snackbarText = MutableLiveData<Event<Any?>>()
    val snackbarText: LiveData<Event<Any?>> = _snackbarText

    private val _showProgress = MutableLiveData<Event<Boolean>>()
    val showProgress: LiveData<Event<Boolean>> = _showProgress

    private val _data = MutableLiveData<Event<String?>>()
    val data: LiveData<Event<String?>> = _data


    fun submitLogin() {
        val value = loginDetails.value
        when {
            TextUtils.isEmpty(value?.mobileNumber?.trim()) -> {
                _snackbarText.value = Event(R.string.mobile_number_validate)
            }
            value?.mobileNumber?.length!! < 10 -> {
                _snackbarText.value = Event(R.string.mobile_number_length_validate)
            }
            else -> {
                _showProgress.value = Event(true)
                _data.value = Event(value.mobileNumber)
                _showProgress.value = Event(false)
            }

        }

    }

    fun verifyOtp() {

        val value = loginDetails.value
        when {
            TextUtils.isEmpty(value?.otp?.trim()) -> {
                _snackbarText.value = Event(R.string.otp_validate)
            }
            value?.otp?.length!! < 4 -> {
                _snackbarText.value = Event(R.string.otp_length_validate)
            }
            else -> {
                _showProgress.value = Event(false)
                prefProvider.setValueboolean(IS_LOGIN, true)
                _data.value = Event(value.otp)


            }
        }
    }
}
