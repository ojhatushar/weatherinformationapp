package com.test.weatherinfo.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.material.navigation.NavigationView
import com.test.weatherinfo.R
import com.test.weatherinfo.databinding.ActivityMainBinding
import com.test.weatherinfo.ui.activities.login.MobileNumberActivity
import com.test.weatherinfo.ui.fragments.HistoryFragment
import com.test.weatherinfo.ui.fragments.MapFragment
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    var activity: Activity = this
    private lateinit var binding: ActivityMainBinding
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var map: GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        actionBarToggle = ActionBarDrawerToggle(this, binding.drawerLayout, 0, 0)
        binding.drawerLayout.addDrawerListener(actionBarToggle)

        setSupportActionBar(binding.toolbar)

        supportActionBar?.setDisplayShowTitleEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)


        actionBarToggle.syncState()

        setupDrawerContent(binding.navigationView)


        binding.lifecycleOwner = this

        val mapFragment = MapFragment()
        replaceFragment(mapFragment)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // The action bar home/up action should open or close the drawer.
        when (item.itemId) {
           /* R.id.menuLogout -> {
                AlertDialogSingout()
                return true
            }*/
            android.R.id.home -> {
                binding.drawerLayout.openDrawer(GravityCompat.START)
                return true

            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setupDrawerContent(navigationView: NavigationView) {
        navigationView.setNavigationItemSelectedListener { menuItem ->
            selectDrawerItem(menuItem)
            true
        }
    }

    private fun selectDrawerItem(menuItem: MenuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        var fragment: Fragment? = null
        val fragmentClass: Class<*> = when (menuItem.itemId) {
            R.id.menuMapScreen -> MapFragment::class.java
            R.id.menuHistScreen -> HistoryFragment::class.java

            else -> MapFragment::class.java
        }
        try {
            fragment = fragmentClass.newInstance() as Fragment
        } catch (e: Exception) {
            e.printStackTrace()
        }

        // Insert the fragment by replacing any existing fragment

        replaceFragment(fragment)


        // Highlight the selected item has been done by NavigationView
        // menuItem.isChecked = true
        // Set action bar title
        title = menuItem.title
        // Close the navigation drawer
        binding.drawerLayout.closeDrawers()
    }

    private fun replaceFragment(fragment: Fragment?) {
        val fragmentManager: FragmentManager = supportFragmentManager
        if (fragment != null) {
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit()
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        TODO("Not yet implemented")
    }

    fun AlertDialogSingout() {
        val builder = AlertDialog.Builder(activity)
        builder.setMessage(getString(R.string.logout_message))
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, id ->
                /*PreferenceUtil.getPref(this@HomeSlider).edit()
                    .putBoolean(PreferenceKeys.IS_LOGIN, false)
                    .putString(PreferenceKeys.USER_FIRSTNAME, "")
                    .putString(PreferenceKeys.USER_EMAIL, "")
                    .putString(PreferenceKeys.USER_MOBILE_NO, "")
                    .putString(PreferenceKeys.USER_PASSWORD, "")
                    .putString(PreferenceKeys.USER_PROFILE_IMAGE, "")
                    .apply()*/
                val myIntent = Intent(activity, MobileNumberActivity::class.java)
                myIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(myIntent)
            }
            .setNegativeButton(
                "No"
            ) { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
}