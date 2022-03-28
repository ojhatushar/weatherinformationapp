package com.test.weatherinfo.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.databinding.FragmentHistoryBinding
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoViewModel
import com.test.weatherinfo.ui.adapters.HistoryScreenListAdapter
import com.test.weatherinfo.utils.ProgressUtils
import com.test.weatherinfo.utils.extensions.liveSnackBar
import com.test.weatherinfo.utils.statusUtils.Status

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by activityViewModels<WeatherInfoViewModel>()
    private lateinit var historyScreenListAdapter: HistoryScreenListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHistoryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        observeShowProgress()
        setupSnackbar()

        setUpRecyclerView()
        getLocationObserver()
        //  binding.viewModel = viewModel
        return binding.root
    }

    private fun setUpRecyclerView() {
        historyScreenListAdapter = HistoryScreenListAdapter()
        binding.rvLocationHistory.adapter = historyScreenListAdapter

    }

    private fun getLocationObserver() {
        viewModel.getLocationHistory.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { historyScreenList ->

                            if (historyScreenList.isNotEmpty()) {
                                setLocationHistory(historyScreenList)
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    "No Location Found",
                                    Toast.LENGTH_LONG
                                ).show()

                            }

                        }
                    }

                    Status.ERROR -> {

                    }

                    Status.LOADING -> {

                    }
                }

            }

        }
    }

    private fun setLocationHistory(locationHistoryList: List<LocationHistoryModel>) {
        historyScreenListAdapter.apply {
            setLocationHistoryData(locationHistoryList)
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