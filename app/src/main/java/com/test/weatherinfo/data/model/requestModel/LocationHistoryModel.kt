package com.test.weatherinfo.data.model.requestModel

import android.util.SparseBooleanArray
import androidx.lifecycle.MutableLiveData
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "LocationHistory")
data class LocationHistoryModel(
    @PrimaryKey(autoGenerate = true)
    var locationId: Int = 0,
    var address: String? = "",
    var latitude: String? = null,
    var longitude: String? = null
)
