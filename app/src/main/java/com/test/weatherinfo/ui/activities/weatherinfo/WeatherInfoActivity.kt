package com.test.weatherinfo.ui.activities.weatherinfo

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.test.weatherinfo.R
import com.test.weatherinfo.databinding.ActivityWeatherInfoBinding
import com.test.weatherinfo.ui.adapters.WeatherInfoViewPagerAdapter
import com.test.weatherinfo.utils.ProgressUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherInfoActivity : AppCompatActivity() {

    var activity: Activity = this
    private lateinit var binding: ActivityWeatherInfoBinding
    var lat: String = ""
    var long: String = ""
    var addresses: String? = ""

    private val viewModel by viewModels<WeatherInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_weather_info)

        binding.lifecycleOwner = this

        lat = intent.getDoubleExtra("lat", 0.0).toString()
        long = intent.getDoubleExtra("long", 0.0).toString()
        addresses = intent.getStringExtra("addresses")


        val viewPager = binding.viewPager
        val tabLayout = binding.tabLayout

        val adapter = WeatherInfoViewPagerAdapter(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = resources.getStringArray(R.array.tabTitle)[position]

        }.attach()


        binding.layoutToolbar.ivBack.setOnClickListener {
            finish()
        }


        ProgressUtils.showProgressDialog(activity)

        viewModel.getCurrentWeatherDetails(lat, long, addresses!!)

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    if (tab.position == 0) {
                        viewModel.getCurrentWeatherDetails(lat, long, addresses!!)
                    } else if (tab.position == 1) {
                        viewModel.getForecastWeatherDetails(lat, long, addresses!!)
                    }

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })
    }


}