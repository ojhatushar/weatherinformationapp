package com.test.weatherinfo.ui.fragments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherinfo.data.repositories.WeatherInfoRepository
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.data.model.responseModel.CurrentWeatherResponse
import com.test.weatherinfo.data.model.responseModel.ForecastWeatherResponse
import com.test.weatherinfo.utils.Event


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapFragmentViewModel @Inject constructor(
    private val weatherInfoRepository: WeatherInfoRepository
) :
    ViewModel() {

    private val _snackbarText = MutableLiveData<Event<Any?>>()
    val snackbarText: LiveData<Event<Any?>> = _snackbarText

    private val _showProgress = MutableLiveData<Event<Boolean>>()
    val showProgress: LiveData<Event<Boolean>> = _showProgress

    private val _dataCurrentWeather = MutableLiveData<Event<CurrentWeatherResponse?>>()
    val dataCurrentCurrentWeather: LiveData<Event<CurrentWeatherResponse?>> = _dataCurrentWeather

    private val _dataForecastWeather = MutableLiveData<Event<ForecastWeatherResponse?>>()
    val dataForecastWeather: LiveData<Event<ForecastWeatherResponse?>> = _dataForecastWeather


    fun saveLocationHistory(lat: String, long: String, addresses: String) {

        viewModelScope.launch {
            val locationData = LocationHistoryModel().apply {
                latitude = lat
                longitude = long
                address = addresses

            }
            weatherInfoRepository.saveLocation(locationData)

        }
    }
}