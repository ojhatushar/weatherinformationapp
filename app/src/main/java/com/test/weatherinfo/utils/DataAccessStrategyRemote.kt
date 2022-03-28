package com.test.weatherinfo.utils

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.test.weatherinfo.utils.statusUtils.Resource
import com.test.weatherinfo.utils.statusUtils.Status


import kotlinx.coroutines.Dispatchers
import java.lang.Exception

fun <T> performGetOperation(databaseQuery: () -> LiveData<T>
    /* networkCall: suspend () -> Resource<A>,
     saveCallResult: suspend (A) -> Unit*/): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val source = databaseQuery.invoke().map { Resource.success(it) }
        emitSource(source)

        /*val responseStatus = networkCall.invoke()
        if (responseStatus.status == SUCCESS) {
            saveCallResult(responseStatus.data!!)

        } else if (responseStatus.status == ERROR) {
            emit(Resource.error(responseStatus.message!!))
            emitSource(source)
        }*/
    }