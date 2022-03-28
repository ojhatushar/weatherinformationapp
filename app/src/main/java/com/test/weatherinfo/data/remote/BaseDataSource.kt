package com.test.weatherinfo.data.remote

import android.util.Log
import com.test.weatherinfo.utils.extensions.NoInternetException
import com.test.weatherinfo.utils.statusUtils.Resource


import org.json.JSONObject
import retrofit2.HttpException
import java.net.SocketTimeoutException

enum class ErrorCodesNew(val code: Int) {
    SocketTimeOut(-1)
}

abstract class BaseDataSource {


    protected suspend fun <T> getResult(call: suspend () -> T): Resource<T> {
        return try {
            Resource.success(call.invoke())
        } catch (e: NoInternetException) {
            handleException(e)
        } catch (e: Exception) {
            Log.d("exception", "::" + e.message)
            handleException(e)
        }
    }

    private fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> Resource.error(
                getErrorMessage(
                    e.response()?.errorBody()?.string(),
                    e.code()
                ), null
            )
            is SocketTimeoutException -> Resource.error(
                getErrorMessage("", ErrorCodesNew.SocketTimeOut.code),
                null
            )

            else -> Resource.error(
                getErrorMessage(
                    "",
                    Int.MAX_VALUE,
                    message = e.message!!
                ), null
            )

            /*else -> Resource.error(
                getErrorMessage(
                    "",
                    Int.MAX_VALUE
                ), null
            )*/
        }
    }


    private fun getErrorMessage(errorBody: String?, code: Int, message: String = ""): String {
        return when (code) {
            ErrorCodesNew.SocketTimeOut.code -> "Timeout"
            401 -> "$code Unauthorised"
            404 -> "$code Not found"
            400 -> showNetworkError(errorBody!!)
            else -> message
        }
    }

    private fun showNetworkError(errorBody: String): String {

        try {
            val jsonObject = JSONObject(errorBody.trim())
            return jsonObject.getString("error")
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return ""
    }



}