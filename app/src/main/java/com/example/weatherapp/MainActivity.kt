package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.WeatherAppData.ApiInterface
import com.example.weatherapp.WeatherAppData.weatherData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Base_URL = "https://api.openweathermap.org/data/2.5/"
class MainActivity : AppCompatActivity() {
    private lateinit var location: ObtainLocation;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        location =  ObtainLocation(this,this@MainActivity)

        var button: Button = findViewById(R.id.button)
        button.setOnClickListener {
            getMyData();

        }
    }

    override fun onStart() {
        super.onStart()
        getMyData();
    }


    private fun getMyData(){

        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Base_URL)
            .build()
            .create(ApiInterface::class.java)

        val retrofitData= retrofitBuilder.getData(latitude=location._latitude.toString(),longitude=location._longitude.toString())
        retrofitData.enqueue(object : Callback<weatherData?> {
            override fun onResponse(
                call: Call<weatherData?>,
                response: Response<weatherData?>
            ) {
                val responseBody = response.body()!!
                Log.d("FailFail", responseBody.main.temp_min.toString())
            }

            override fun onFailure(call: Call<weatherData?>, t: Throwable) {
                Log.d("FailFail", t.toString())
            }
        })
    }
}