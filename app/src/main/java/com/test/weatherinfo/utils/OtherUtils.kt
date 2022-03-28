package com.test.weatherinfo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.LocationManager
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import java.io.UnsupportedEncodingException
import java.lang.StringBuilder
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object OtherUtils {

    fun getAndroidId(context: Context): String? {
        return Settings.Secure.getString(
            context.contentResolver,
            Settings.Secure.ANDROID_ID
        )
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class)
    fun convertSHA256(text: String): String? {
        val md = MessageDigest.getInstance("SHA-256")
        md.update(text.toByteArray())
        val digest = md.digest()

        // return Utils.toHexString(md.digest(text.getBytes("UTF-8")));
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            toHexStringTest(md.digest(text.toByteArray(StandardCharsets.UTF_8)))
        } else {
            toHexStringTest(md.digest(text.toByteArray(charset("UTF-8"))))
        }
        // return Base64.encodeToString(digest, Base64.DEFAULT);
    }

    fun toHexStringTest(hash: ByteArray): String? {
        val sb = StringBuilder()
        for (b in hash) {
            sb.append(String.format("%02x", b))
        }
        return sb.toString()
    }

    fun bitmapDescriptorFromVector(context: Context?, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(
            context!!, vectorResId
        )
        vectorDrawable!!.setBounds(
            0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight
        )
        val bitmap = Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    fun isLocationEnabled(context: Context): Boolean {
        val locationManager: LocationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}