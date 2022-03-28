package com.test.weatherinfo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.test.weatherinfo.data.model.responseModel.ForecastWeatherResponse
import com.test.weatherinfo.databinding.FragmentForcastWeatherBinding
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoViewModel
import com.test.weatherinfo.ui.adapters.ForecastWeatherListAdapter
import com.test.weatherinfo.utils.ProgressUtils
import com.test.weatherinfo.utils.extensions.liveSnackBar
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [MobileNumberFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ForecastWeatherFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentForcastWeatherBinding
    private val viewModel by activityViewModels<WeatherInfoViewModel>()
    private lateinit var forecastWeatherListAdapter: ForecastWeatherListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentForcastWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        observeShowProgress()
        setupSnackbar()
        showDataOnUi()
        setUpRecyclerView()
        //  binding.viewModel = viewModel
        return binding.root

    }

    private fun setUpRecyclerView() {
        forecastWeatherListAdapter = ForecastWeatherListAdapter()
        binding.rvForecastWeather.adapter = forecastWeatherListAdapter

    }

    private fun showDataOnUi() {
        viewModel.dataForecastWeather.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { forecastWeatherResponse ->

                setForecastWeather(forecastWeatherResponse.list.take(5))
            }
        }
    }

    private fun setForecastWeather(list: List<ForecastWeatherResponse.Any>) {
        forecastWeatherListAdapter.apply {
            setForecastWeatherData(list)
            notifyDataSetChanged()
        }
    }

    private fun observeShowProgress() {
        viewModel.showProgress.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                if (it) {
                    ProgressUtils.showProgressDialog(requireActivity())
                } else {
                    ProgressUtils.dismissProgressDialog()
                }
            }
        }
    }


    private fun setupSnackbar() {
        binding.root.liveSnackBar(this, viewModel.snackbarText, Snackbar.LENGTH_SHORT)

    }

}