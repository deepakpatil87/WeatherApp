package com.hexaware.weatherapp.model.forecast

data class ForecastRes(
    val city: City,
    val cnt: Int,
    val cod: String,
    val list: List<ListW>,
    val message: Int
)