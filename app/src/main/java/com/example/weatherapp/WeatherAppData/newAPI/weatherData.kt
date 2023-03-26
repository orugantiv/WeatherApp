package com.example.weatherapp.WeatherAppData.newAPI

data class weatherData(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)