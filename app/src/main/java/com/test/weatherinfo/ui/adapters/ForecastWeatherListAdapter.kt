package com.test.weatherinfo.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.test.weatherinfo.data.model.responseModel.ForecastWeatherResponse
import com.test.weatherinfo.databinding.ItemForecastWeatherBinding

class ForecastWeatherListAdapter :
    RecyclerView.Adapter<ForecastWeatherListAdapter.MyViewHolder>() {


    var forecastWeatherList = ArrayList<ForecastWeatherResponse.Any>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ForecastWeatherListAdapter.MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemForecastWeatherBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ForecastWeatherListAdapter.MyViewHolder,
        position: Int
    ) {
        val itemBinding = holder.itemForecastWeatherBinding

        itemBinding.forecastWeatherModel = forecastWeatherList[position].weather[0]

        val dateTime = forecastWeatherList[position].dtTxt.split(" ").toTypedArray()


        itemBinding.tvDate.text = dateTime[0] + "\n" + dateTime[1]


        /*Glide.with(itemBinding.root.context)
            .load("http://openweathermap.org/img/w/04d.png") // Uri of the picture
            .into(itemBinding.ivImageIcon)*/


        /*String iconUrl = "http://openweathermap.org/img/w/" + iconCode + ".png";*/
        itemBinding.executePendingBindings()
    }

    override fun getItemCount() = forecastWeatherList.size

    fun setForecastWeatherData(forecastWeatherList: List<ForecastWeatherResponse.Any>) {

        this.forecastWeatherList.apply {
            clear()
            addAll(forecastWeatherList)
        }
    }


    inner class MyViewHolder(val itemForecastWeatherBinding: ItemForecastWeatherBinding) :
        RecyclerView.ViewHolder(itemForecastWeatherBinding.root) {

    }
}