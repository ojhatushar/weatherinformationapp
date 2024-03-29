package com.test.weatherinfo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.test.weatherinfo.databinding.FragmentCurrentWeatherBinding
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoViewModel
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
class CurrentWeatherFragment : Fragment() {

    private lateinit var binding: FragmentCurrentWeatherBinding
    private val viewModel by activityViewModels<WeatherInfoViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        observeShowProgress()
        setupSnackbar()
        showDataOnUi()
        //  binding.viewModel = viewModel
        return binding.root
    }

    private fun showDataOnUi() {
        viewModel.dataCurrentCurrentWeather.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { currentWeather ->
                binding.weatherModelData = currentWeather.weather[0]
            }
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