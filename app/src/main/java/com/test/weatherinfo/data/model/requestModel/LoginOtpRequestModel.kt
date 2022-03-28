package com.test.weatherinfo.data.model.requestModel

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Login")
data class LoginOtpRequestModel(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var mobileNumber: String? = "",
    var otp: String? = "",
)