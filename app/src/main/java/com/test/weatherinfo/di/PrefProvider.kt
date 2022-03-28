package com.test.weatherinfo.di

import android.content.Context
import android.content.SharedPreferences
import com.test.weatherinfo.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PrefProvider @Inject constructor(@ApplicationContext context: Context) {

    private var sharedPreferences: SharedPreferences? = null
    private var mContext = context

    private fun openPref() {
        sharedPreferences = mContext.getSharedPreferences(
            mContext.resources.getString(R.string.app_name),
            Context.MODE_PRIVATE
        )
    }

    fun getValue(
        key: String?,
        defaultValue: String?
    ): String? {
        openPref()
        val result = sharedPreferences?.getString(key, defaultValue)
        sharedPreferences = null
        return result
    }

    fun getValueInt(
        key: String?,
        defaultValue: Int
    ): Int {
        openPref()
        val result = sharedPreferences!!.getInt(key, defaultValue)
        sharedPreferences = null
        return result
    }

    fun getValueLong(
        key: String?,
        defaultValue: Long
    ): Long {
        openPref()
        val result = sharedPreferences!!.getLong(key, defaultValue)
        sharedPreferences = null
        return result
    }


    fun setValue(
        key: String?,
        value: String?
    ) {
        openPref()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putString(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun setValueInt(
        key: String?,
        value: Int
    ) {
        openPref()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putInt(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun setValueLong(
        key: String?,
        value: Long
    ) {
        openPref()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putLong(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }

    fun getValueboolean(
        key: String?,
        defaultValue: Boolean
    ): Boolean {
        openPref()
        val result = sharedPreferences!!.getBoolean(key, defaultValue)
        sharedPreferences = null
        return result
    }

    fun setValueboolean(

        key: String?,
        value: Boolean
    ) {
        openPref()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor!!.putBoolean(key, value)
        prefsPrivateEditor.apply()
        sharedPreferences = null
    }


    fun setClear() {
        openPref()
        val prefsPrivateEditor = sharedPreferences!!.edit()
        prefsPrivateEditor!!.clear().apply()
        sharedPreferences = null
    }

}