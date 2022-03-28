package com.test.weatherinfo.ui.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.permissionx.guolindev.PermissionX
import com.test.weatherinfo.R
import com.test.weatherinfo.databinding.FragmentForcastWeatherBinding
import com.test.weatherinfo.databinding.FragmentMapBinding
import com.test.weatherinfo.ui.activities.MainActivity
import com.test.weatherinfo.ui.activities.login.OtpActivity
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoActivity
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoViewModel
import com.test.weatherinfo.utils.CustomDialogFragment
import com.test.weatherinfo.utils.OtherUtils.isLocationEnabled
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var binding: FragmentMapBinding
    private lateinit var map: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var mMarker: Marker? = null
    private var requestedPosition: LatLng? = null
    var addresses: List<Address> = emptyList()
    private val viewModel by activityViewModels<MapFragmentViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentMapBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this

        //  val v: View = inflater.inflate(R.layout.fragment_map, container, false)

        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        supportMapFragment?.getMapAsync(this)

        (activity as MainActivity).supportActionBar?.title =
            resources.getString(R.string.map_screen)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())



        binding.btnChoose.setOnClickListener {
            if (addresses.isNotEmpty()) {
                val intent = Intent(requireActivity(), WeatherInfoActivity::class.java)
                intent.putExtra("lat", requestedPosition?.latitude)
                intent.putExtra("long", requestedPosition?.longitude)
                intent.putExtra("addresses", addresses[0].getAddressLine(0))

                viewModel.saveLocationHistory(
                    String.format("%.4f", requestedPosition?.latitude),
                    String.format("%.4f", requestedPosition?.longitude),
                    addresses[0].getAddressLine(0)
                )
                startActivity(intent)
            }
        }

        return binding.root

    }


    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        permissionRequired()

        map.setOnMarkerClickListener { false }
        map.isMyLocationEnabled = true
        map.setOnCameraIdleListener {
            val lat: Double = map.cameraPosition.target.latitude
            val lng: Double = map.cameraPosition.target.longitude

            requestedPosition = LatLng(lat, lng)
            if (requestedPosition != null) {
                updateMarker(requestedPosition!!)
            }
        }

    }

    fun permissionRequired() {
        PermissionX.init(this)
            .permissions(
                Manifest.permission.ACCESS_FINE_LOCATION,
            )
            .setDialogTintColor(Color.parseColor("#1972e8"), Color.parseColor("#8ab6f5"))
            .onExplainRequestReason { scope, deniedList, beforeRequest ->
                val message = "PermissionX needs following permissions to continue"
                scope.showRequestReasonDialog(deniedList, message, "Allow", "Deny")

            }
            .onForwardToSettings { scope, deniedList ->
                val message = "Please allow following permissions in settings"
                val dialog = CustomDialogFragment(message, deniedList)
                scope.showForwardToSettingsDialog(dialog)
            }
            .request { allGranted, grantedList, deniedList ->
                if (allGranted) {
                    statusCheck()
                } else {
                    requireActivity().finish()
                    Toast.makeText(
                        activity,
                        "The following permissions are denied：$deniedList",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }


    private fun statusCheck() {
        if (isLocationEnabled(requireActivity())) {
            requestCurrentLocation()
        } else {
            showGPSNotEnabledDialog(requireActivity())
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestCurrentLocation() {
        fusedLocationClient.lastLocation
            .addOnCompleteListener { task ->
                val location = task.result
                if (location == null) {
                    requestNewLocationData()
                } else {
                    requestedPosition = LatLng(location.latitude, location.longitude)
                    map.clear()
                    map.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            requestedPosition!!,
                            18F
                        )
                    )

                    mMarker = map.addMarker(MarkerOptions().position(requestedPosition!!))

                }
            }

    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest.create().apply {
            interval = 2000
            fastestInterval = 2000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            numUpdates = 1
        }
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            object : LocationCallback() {
                override fun onLocationResult(locationResult: LocationResult) {
                    super.onLocationResult(locationResult)
                    for (location in locationResult.locations) {
                        Log.d("location", "::" + location.latitude + "::" + location.longitude)
                    }
                    // Few more things we can do here:
                    // For example: Update the location of user on server
                }
            },
            Looper.myLooper()!!
        )
    }

    // Function to show the "enable GPS" Dialog box
    fun showGPSNotEnabledDialog(context: Context) {
        AlertDialog.Builder(context)
            .setTitle("Enable Gps")
            .setMessage("Required for this app")
            .setCancelable(false)
            .setPositiveButton("Enable Now") { _, _ ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1)
            }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1) {
            if (resultCode == RESULT_CANCELED) {
                statusCheck()
            }
        }
    }

    private fun updateMarker(position: LatLng) {
        mMarker?.position = position
        val geocoder = Geocoder(activity, Locale.getDefault())

        try {

            addresses = geocoder.getFromLocation(
                position.latitude,
                position.longitude,
                1
            )
        } catch (ioException: IOException) {
            // Catch network or other I/O problems.
            Log.e("Exception", "::$ioException")
        } catch (illegalArgumentException: IllegalArgumentException) {
            // Catch invalid latitude or longitude values.
            Log.e("Exception", "::$illegalArgumentException")

        } catch (e: Exception) {
            Log.e("Exception", "::$e")
        }
        Log.d("addresses", "::" + addresses)

        //   val address = addresses[0].getAddressLine(0)
        mMarker?.title = addresses[0].getAddressLine(0)
        if (mMarker != null && mMarker!!.isInfoWindowShown()) {
            mMarker!!.showInfoWindow();
        }

    }
}