package com.hexaware.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.hexaware.weatherapp.api.WebApi
import com.hexaware.weatherapp.model.current.WeatherData
import com.pivincii.livedata_retrofit.network.ApiResponse
import com.hexaware.weatherapp.api.RetrofitClient
import com.hexaware.weatherapp.model.forecast.ForecastRes


class WeatherDetailViewModel() : ViewModel() {


    private val retrofitService: WebApi = RetrofitClient().getRetrofit().create(WebApi::class.java)

//http://api.openweathermap.org/data/2.5/weather?lat=22.7196&lon=75.8577&appid=fae7190d7e6433ec3a45285ffcf55c86
    fun callCurrentWeatherAPI(
    lat: Double,
    lon: Double,
    units: String,
    appid:String
    ): LiveData<ApiResponse<WeatherData>> {
        return retrofitService.getCurrentWeatherDetails(lat, lon, units,appid)
    }

   // http://api.openweathermap.org/data/2.5/forecast?lat=22.7196&lon=75.8577&appid=fae7190d7e6433ec3a45285ffcf55c86&units=metric
   fun callForecastWeatherAPI(
       lat: Double,
       lon: Double,
       units: String,
       appid:String
   ): LiveData<ApiResponse<ForecastRes>> {
       return retrofitService.getForecastDetails(lat, lon, units,appid)
   }
}



