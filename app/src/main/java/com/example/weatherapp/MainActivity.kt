package com.example.weatherapp

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.weatherapp.WeatherAppData.ApiInterface
import com.example.weatherapp.WeatherAppData.weatherData
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Base_URL = "https://api.openweathermap.org/data/2.5/ "
class MainActivity : AppCompatActivity() {
    private lateinit var location: ObtainLocation;
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    val retrofitBuilder = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(Base_URL)
        .build()
        .create(ApiInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        var button: Button = findViewById(R.id.refresh_button)

        button.setOnClickListener {
            getMyData();
        }
    }


    fun updateCurrentFields(data:weatherData){

        var locationTextField: TextView = findViewById(R.id.location);
        locationTextField.text = "Location: \n"+ "Lon: "+ data.coord.lon+" Lat: "+ data.coord.lat  ;

        var conditionTextField: TextView = findViewById(R.id.condition);
        conditionTextField.text = "Condition: \n"+ data.weather[0].description.toString()  ;

        var currentTempTextField: TextView = findViewById(R.id.currentTemp);
        currentTempTextField.text =  data.main.temp.toString() + " °F"

        var minTempTempTextField: TextView = findViewById(R.id.minTemp);
        minTempTempTextField.text =  "Min: "+ data.main.temp_min.toString() + " °F"

        var maxTempTempTextField: TextView = findViewById(R.id.maxTemp);
        maxTempTempTextField.text = "Max: "+ data.main.temp_max.toString() + " °F"

    }
    override fun onStart() {
        super.onStart()
        getMyData();
    }

    private fun getLocation(){
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )

        }


        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    Log.d("current", location.longitude.toString())
                }

            }
    }
    private fun getMyData(){
        location =  ObtainLocation(this,this@MainActivity)
        getLocation()
        val retrofitData= retrofitBuilder.getData(latitude=location._latitude.toString(),longitude=location._longitude.toString())
        retrofitData.enqueue(object : Callback<weatherData?> {

            override fun onResponse(
                call: Call<weatherData?>,
                response: Response<weatherData?>
            ) {
                val responseBody = response.body()!!
                updateCurrentFields(responseBody)
            }
            override fun onFailure(call: Call<weatherData?>, t: Throwable) {
                Log.d("FailFail", t.toString())
            }
        })
    }
}