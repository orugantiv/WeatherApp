package com.example.weatherapp

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.weatherapp.WeatherAppData.ApiInterface
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val Base_URL = "https://api.weatherapi.com/v1/"
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
        var button: Button = findViewById(R.id.refresh_button)

        button.setOnClickListener {
            getMyData();
        }
    }


    fun updateCurrentFields(data: com.example.weatherapp.WeatherAppData.newAPI.weatherData){

        var locationTextField: TextView = findViewById(R.id.location);
        locationTextField.text =  "\uD83D\uDCCD "+data.location.name+", "+data.location.region+",\n "+data.location.country//"Location: \n"+ "Lon: "+ data. .lon+" Lat: "+ data.coord.lat  ;

        var conditionTextField: TextView = findViewById(R.id.condition);
        conditionTextField.text = "Condition: \n"+ data.current.condition.text  ;
//
        var currentTempTextField: TextView = findViewById(R.id.currentTemp);
        currentTempTextField.text =  data.current.temp_f.toString() + " °F" //main.temp.toString() + " °F"
//
        var minTempTempTextField: TextView = findViewById(R.id.minTemp);
        minTempTempTextField.text =  "Wind: "+ data.current.wind_mph.toString() +" "+data.current.wind_dir // main.temp_min.toString() + " °F"
//
        var maxTempTempTextField: TextView = findViewById(R.id.maxTemp);
        maxTempTempTextField.text =  "Humidity: "+ data.current.humidity.toString() //"Max: "+ data.main.temp_max.toString() + " °F"

    }
    override fun onStart() {
        super.onStart()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this@MainActivity)
        location = ObtainLocation(this,this)
        getMyData();
    }

    private fun getMyData(){
        location.updateLocation()

        val loc = location._latitude.toString()+ ","+ location._longitude.toString()
        Log.d("Current Location: ",loc)
        val retrofitData= retrofitBuilder.getData(q=loc)

        retrofitData.enqueue(object : Callback<com.example.weatherapp.WeatherAppData.newAPI.weatherData?> {

            override fun onResponse(
                call: Call<com.example.weatherapp.WeatherAppData.newAPI.weatherData?>,
                response: Response<com.example.weatherapp.WeatherAppData.newAPI.weatherData?>
            ) {
                val responseBody = response.body()!!
                updateCurrentFields(responseBody)
            }
            override fun onFailure(call: Call<com.example.weatherapp.WeatherAppData.newAPI.weatherData?>, t: Throwable) {
                Log.d("FailFail", t.toString())
            }
        })
    }
}