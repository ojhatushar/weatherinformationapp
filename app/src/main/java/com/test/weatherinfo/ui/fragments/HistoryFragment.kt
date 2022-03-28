package com.test.weatherinfo.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.test.weatherinfo.R
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.databinding.FragmentHistoryBinding
import com.test.weatherinfo.ui.activities.MainActivity
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoActivity
import com.test.weatherinfo.ui.activities.weatherinfo.WeatherInfoViewModel
import com.test.weatherinfo.ui.adapters.HistoryScreenListAdapter
import com.test.weatherinfo.ui.interfaces.ItemClickListener
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
class HistoryFragment : Fragment(), ItemClickListener {
    // TODO: Rename and change types of parameters

    private lateinit var binding: FragmentHistoryBinding
    private val viewModel by activityViewModels<WeatherInfoViewModel>()
    private lateinit var historyScreenListAdapter: HistoryScreenListAdapter
    private lateinit var historySCreenList: ArrayList<LocationHistoryModel>

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
        (activity as MainActivity).supportActionBar?.title = getString(R.string.history_screen)
        return binding.root
    }

    private fun setUpRecyclerView() {
        historyScreenListAdapter = HistoryScreenListAdapter(this)
        binding.rvLocationHistory.adapter = historyScreenListAdapter

    }

    private fun getLocationObserver() {
        viewModel.getLocationHistory.observe(viewLifecycleOwner) {
            it?.let { resource ->
                when (resource.status) {
                    Status.SUCCESS -> {
                        resource.data?.let { locationHistoryList ->

                            if (locationHistoryList.isNotEmpty()) {
                                historySCreenList =
                                    locationHistoryList as ArrayList<LocationHistoryModel>
                                setLocationHistory(locationHistoryList)
                            } else {
                                Toast.makeText(
                                    requireActivity(),
                                    getString(R.string.no_location),
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

    override fun onClick(position: Int) {
        val intent = Intent(requireActivity(), WeatherInfoActivity::class.java)
        intent.putExtra("lat", historySCreenList[position].latitude)
        intent.putExtra("long", historySCreenList[position].longitude)
        intent.putExtra("addresses", historySCreenList[position].address)
        startActivity(intent)
    }

}