package com.test.weatherinfo.ui.activities.weatherinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.weatherinfo.data.repositories.WeatherInfoRepository
import com.test.weatherinfo.data.model.responseModel.CurrentWeatherResponse
import com.test.weatherinfo.data.model.responseModel.ForecastWeatherResponse
import com.test.weatherinfo.data.remote.Constants.API_KEY_WEATHER
import com.test.weatherinfo.utils.Event
import com.test.weatherinfo.utils.statusUtils.Status


import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherInfoViewModel @Inject constructor(
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

    var getLocationHistory = weatherInfoRepository.getLocation()


    fun getCurrentWeatherDetails(lat: String, long: String, addresses: String) {

        val data = HashMap<String, String>()
        data["lat"] = lat
        data["lon"] = long
        data["appid"] = API_KEY_WEATHER
        viewModelScope.launch {
            val resource = weatherInfoRepository.getCurrentWeatherData(data)

            when (resource.status) {
                Status.SUCCESS -> {
                    _showProgress.value = Event(false)
                    resource.data.let { weatherResponse ->
                        resource.data?.let {

                            _dataCurrentWeather.value = Event(it)
                        }
                    }
                }

                Status.ERROR -> {
                    _snackbarText.value = Event(resource.message)
                    _showProgress.value = Event(false)
                }

                Status.LOADING -> {
                    _showProgress.value = Event(true)
                }
            }
        }
    }

    fun getForecastWeatherDetails(lat: String, long: String, addresses: String) {

        val data = HashMap<String, String>()
        data["lat"] = lat
        data["lon"] = long
        data["appid"] = API_KEY_WEATHER
        viewModelScope.launch {
            val resource = weatherInfoRepository.getForecastWeatherData(data)

            when (resource.status) {
                Status.SUCCESS -> {
                    _showProgress.value = Event(false)
                    resource.data.let { weatherResponse ->
                        resource.data?.let {
                            _dataForecastWeather.value = Event(it)

                        }
                    }
                }

                Status.ERROR -> {
                    _snackbarText.value = Event(resource.message)
                    _showProgress.value = Event(false)
                }

                Status.LOADING -> {
                    _showProgress.value = Event(true)
                }
            }
        }
    }
}