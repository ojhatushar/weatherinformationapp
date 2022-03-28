package com.test.weatherinfo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.test.weatherinfo.data.model.requestModel.LocationHistoryModel
import com.test.weatherinfo.databinding.ItemLocationHistoryBinding
import com.test.weatherinfo.ui.interfaces.ItemClickListener

class HistoryScreenListAdapter(var itemClickListener: ItemClickListener) :
    RecyclerView.Adapter<HistoryScreenListAdapter.MyViewHolder>() {


    var historyScreenList = ArrayList<LocationHistoryModel>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HistoryScreenListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationHistoryBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: HistoryScreenListAdapter.MyViewHolder,
        position: Int
    ) {
        val itemBinding = holder.itemLocationHistoryBinding

        itemBinding.locationHistoryModel = historyScreenList[position]

        itemBinding.root.setOnClickListener {
            itemClickListener.onClick(position)
        }

        itemBinding.executePendingBindings()
    }

    override fun getItemCount() = historyScreenList.size

    fun setLocationHistoryData(locationHistoryList: List<LocationHistoryModel>) {

        this.historyScreenList.apply {
            clear()
            addAll(locationHistoryList)
        }
    }

    inner class MyViewHolder(val itemLocationHistoryBinding: ItemLocationHistoryBinding) :
        RecyclerView.ViewHolder(itemLocationHistoryBinding.root) {

    }
}