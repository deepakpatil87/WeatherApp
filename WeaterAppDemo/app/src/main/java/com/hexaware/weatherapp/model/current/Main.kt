package com.hexaware.weatherapp.model.current

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Main(
    val feels_like: Double,
    val humidity: Int,
    val pressure: Int,
    val temp: Double,
    val temp_max: Double,
    val temp_min: Double
): Parcelable {
    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return "$humidity°"
    }

    fun getTempMinString(): String {
        return temp_min.toString().substringBefore(".") + "°"
    }

    fun getTempMaxString(): String {
        return temp_max.toString().substringBefore(".") + "°"
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}