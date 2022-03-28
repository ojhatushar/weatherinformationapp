package com.test.weatherinfo.data.remote

import javax.inject.Inject

class ApiHelperNew @Inject constructor(private val apiService: ApiServieNew) : BaseDataSource() {

    suspend fun getCurrentWeatherData(data: HashMap<String, String>) =
        getResult { apiService.getCurrentWeatherData(data) }

    suspend fun getForecastWeatherData(data: HashMap<String, String>) =
        getResult { apiService.getForecastWeatherData(data) }

    // suspend fun sendOtp(data: HashMap<String, String>) = getResult{apiService.sendOTP(data)}
}