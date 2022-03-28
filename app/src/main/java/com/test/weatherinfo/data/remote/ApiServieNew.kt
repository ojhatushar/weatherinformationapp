package com.test.weatherinfo.data.remote


import com.test.weatherinfo.data.model.responseModel.CurrentWeatherResponse
import com.test.weatherinfo.data.model.responseModel.ForecastWeatherResponse
import retrofit2.http.*


interface ApiServieNew {

    @GET("weather?")
   suspend fun getCurrentWeatherData(
        @QueryMap options: Map<String, String>
    ): CurrentWeatherResponse

    @GET("forecast?")
    suspend  fun getForecastWeatherData(
        @QueryMap options: Map<String, String>
    ): ForecastWeatherResponse

}