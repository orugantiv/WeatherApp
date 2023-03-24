package com.example.weatherapp

import android.content.pm.PackageManager
import android.location.Location
import androidx.core.app.ActivityCompat
import android.Manifest
import android.content.Context
import android.util.Log
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class ObtainLocation(private val context: Context, private val activity: MainActivity) {
    private var fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

     var _latitude: Double = 0.0;
     var _longitude: Double = 0.0;
    init {
        updateLocation()
    }
    fun updateLocation() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1
            )

        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    _latitude = location!!.latitude
                    Log.d("current", _latitude.toString())
                    _longitude = location!!.longitude
                }
            }
    }

}