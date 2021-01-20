package com.hexaware.weatherapp.api


import androidx.lifecycle.LiveData
import com.hexaware.weatherapp.model.current.WeatherData
import com.hexaware.weatherapp.model.forecast.ForecastRes
import com.pivincii.livedata_retrofit.network.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WebApi {

    //http://api.openweathermap.org/data/2.5/weather?lat=22.7196&lon=75.8577&appid=fae7190d7e6433ec3a45285ffcf55c86
    // Drive Get Status Graph and status
    @GET("data/2.5/weather")
    fun getCurrentWeatherDetails(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String,

        ): LiveData<ApiResponse<WeatherData>>

    @GET("data/2.5/forecast")
    fun getForecastDetails(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String,
        @Query("appid") appid: String,

        ): LiveData<ApiResponse<ForecastRes>>
}