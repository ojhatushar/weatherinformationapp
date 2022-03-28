package com.test.weatherinfo.data.model.responseModel


import com.google.gson.annotations.SerializedName

data class CurrentWeatherResponse(
    @SerializedName("base")
    val base: String, // stations
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("cod")
    val cod: Int, // 200
    @SerializedName("coord")
    val coord: Coord,
    @SerializedName("dt")
    val dt: Int, // 1648298556
    @SerializedName("id")
    val id: Int, // 1851632
    @SerializedName("main")
    val main: Main,
    @SerializedName("name")
    val name: String, // Shuzenji
    @SerializedName("rain")
    val rain: Rain,
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("timezone")
    val timezone: Int, // 32400
    @SerializedName("visibility")
    val visibility: Int, // 1218
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
) {
    data class Clouds(
        @SerializedName("all")
        val all: Int // 100
    )

    data class Coord(
        @SerializedName("lat")
        val lat: Double, // 35
        @SerializedName("lon")
        val lon: Double // 139
    )

    data class Main(
        @SerializedName("feels_like")
        val feelsLike: Double, // 288.35
        @SerializedName("humidity")
        val humidity: Double, // 94
        @SerializedName("pressure")
        val pressure: Double, // 1003
        @SerializedName("temp")
        val temp: Double, // 288.32
        @SerializedName("temp_max")
        val tempMax: Double, // 288.32
        @SerializedName("temp_min")
        val tempMin: Double // 288.32
    )

    data class Rain(
        @SerializedName("1h")
        val h: Double // 1.62
    )

    data class Sys(
        @SerializedName("country")
        val country: String, // JP
        @SerializedName("id")
        val id: Int, // 2019346
        @SerializedName("sunrise")
        val sunrise: Int, // 1648240799
        @SerializedName("sunset")
        val sunset: Int, // 1648285170
        @SerializedName("type")
        val type: Int // 2
    )

    data class Weather(
        @SerializedName("description")
        val description: String, // moderate rain
        @SerializedName("icon")
        val icon: String, // 10n
        @SerializedName("id")
        val id: Int, // 501
        @SerializedName("main")
        val main: String // Rain
    )

    data class Wind(
        @SerializedName("deg")
        val deg: Double, // 18
        @SerializedName("gust")
        val gust: Double, // 0.45
        @SerializedName("speed")
        val speed: Double // 0.45
    )
}