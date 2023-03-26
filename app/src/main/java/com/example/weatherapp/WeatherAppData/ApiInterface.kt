package com.example.weatherapp.WeatherAppData

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
//    @GET("weather")
//    fun getData(
//        @Query("lat") latitude : String,
//        @Query("lon") longitude: String,
//        @Query("units") units: String = "imperial",
//        @Query("appid") appId: String = "423ade1744c166a3f26c20d85331b9a9"
//    ): Call<weatherData>

    @GET(" forecast.json")
    fun getData(
        @Query("q") q : String,
        @Query("days") days: String = "1",
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no",
        @Query("key") appId: String = "ff6e2157a81447518ba74706232603"
    ): Call<com.example.weatherapp.WeatherAppData.newAPI.weatherData>



}