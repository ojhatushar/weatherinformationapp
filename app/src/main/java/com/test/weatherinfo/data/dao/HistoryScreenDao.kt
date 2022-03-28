package com.test.weatherinfo.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel


@Dao
interface HistoryScreenDao {

    @Insert
    suspend fun saveLocation(locationHistoryModel: LocationHistoryModel): Long

    @Query("SELECT * FROM LocationHistory")
    fun getLocation(): LiveData<List<LocationHistoryModel>>

}