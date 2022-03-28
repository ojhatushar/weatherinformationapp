package com.example.financialinvestment.data.repositories


import com.test.weatherinfo.data.db.AppDatabase
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.data.remote.ApiHelperNew
import com.test.weatherinfo.utils.performGetOperation


import javax.inject.Inject


class WeatherInfoRepository @Inject constructor(
    private val appDatabase: AppDatabase,
    private val apiHelperNew: ApiHelperNew
) {
    suspend fun getCurrentWeatherData(data: HashMap<String, String>) =
        apiHelperNew.getCurrentWeatherData(data)

    suspend fun getForecastWeatherData(data: HashMap<String, String>) =
        apiHelperNew.getForecastWeatherData(data)


    suspend fun saveLocation(locationHistoryModel: LocationHistoryModel) =
        appDatabase.historyScreenDao().saveLocation(locationHistoryModel)

    fun getLocation() = performGetOperation(
        databaseQuery = { appDatabase.historyScreenDao().getLocation() })
}