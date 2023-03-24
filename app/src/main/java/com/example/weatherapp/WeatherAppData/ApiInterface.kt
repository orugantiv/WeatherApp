package com.example.weatherapp.WeatherAppData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun getData(
        @Query("lat") latitude : String,
        @Query("lon") longitude: String,

        @Query("appid") appId: String = "423ade1744c166a3f26c20d85331b9a9"
    ): Call<weatherData>
}