package com.test.weatherinfo.data.model.responseModel


import com.google.gson.annotations.SerializedName

data class ForecastWeatherResponse(
    @SerializedName("city")
    val city: City,
    @SerializedName("cnt")
    val cnt: Int, // 40
    @SerializedName("cod")
    val cod: String, // 200
    @SerializedName("list")
    val list: List<Any>,
    @SerializedName("message")
    val message: Int // 0
) {
    data class City(
        @SerializedName("coord")
        val coord: Coord,
        @SerializedName("country")
        val country: String, // JP
        @SerializedName("id")
        val id: Int, // 1851632
        @SerializedName("name")
        val name: String, // Shuzenji
        @SerializedName("population")
        val population: Int, // 0
        @SerializedName("sunrise")
        val sunrise: Int, // 1648327114
        @SerializedName("sunset")
        val sunset: Int, // 1648371617
        @SerializedName("timezone")
        val timezone: Int // 32400
    ) {
        data class Coord(
            @SerializedName("lat")
            val lat: Double, // 35
            @SerializedName("lon")
            val lon: Double // 139
        )
    }

    data class Any (
        @SerializedName("clouds")
        val clouds: Clouds,
        @SerializedName("dt")
        val dt: Int, // 1648328400
        @SerializedName("dt_txt")
        val dtTxt: String, // 2022-03-26 21:00:00
        @SerializedName("main")
        val main: Main,
        @SerializedName("pop")
        val pop: Double, // 0
        @SerializedName("rain")
        val rain: Rain,
        @SerializedName("sys")
        val sys: Sys,
        @SerializedName("visibility")
        val visibility: Int, // 2186
        @SerializedName("weather")
        val weather: List<Weather>,
        @SerializedName("wind")
        val wind: Wind
    ) {
        data class Clouds(
            @SerializedName("all")
            val all: Int // 95
        )

        data class Main(
            @SerializedName("feels_like")
            val feelsLike: Double, // 289.25
            @SerializedName("grnd_level")
            val grndLevel: Double, // 973
            @SerializedName("humidity")
            val humidity: Double, // 96
            @SerializedName("pressure")
            val pressure: Double, // 997
            @SerializedName("sea_level")
            val seaLevel: Double, // 997
            @SerializedName("temp")
            val temp: Double, // 289.09
            @SerializedName("temp_kf")
            val tempKf: Double, // 0.71
            @SerializedName("temp_max")
            val tempMax: Double, // 289.09
            @SerializedName("temp_min")
            val tempMin: Double // 288.38
        )

        data class Rain(
            @SerializedName("3h")
            val h: Double // 0.31
        )

        data class Sys(
            @SerializedName("pod")
            val pod: String // d
        )

        data class Weather(
            @SerializedName("description")
            val description: String, // overcast clouds
            @SerializedName("icon")
            val icon: String, // 04d
            @SerializedName("id")
            val id: Int, // 804
            @SerializedName("main")
            val main: String // Clouds
        )

        data class Wind(
            @SerializedName("deg")
            val deg: Double, // 205
            @SerializedName("gust")
            val gust: Double, // 2.23
            @SerializedName("speed")
            val speed: Double // 1.32
        )
    }
}