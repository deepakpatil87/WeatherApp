package com.hexaware.weatherapp.model.current

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.text.SimpleDateFormat

import java.time.format.TextStyle
import java.util.*

@Parcelize
data class WeatherData(
    val base: @RawValue String,
    val clouds:@RawValue  Clouds,
    val cod: @RawValue Int,
    val coord: @RawValue Coord,
    val dt:@RawValue Long,
    val id: @RawValue Int,
    val main: @RawValue Main,
    val name:@RawValue  String,
    val sys: @RawValue Sys,
    val timezone:@RawValue Int,
    val visibility:@RawValue Int,
    val weather:@RawValue List<Weather>,
    val wind:@RawValue Wind
): Parcelable {
    fun getDay(): String? {
        return dt.let { getDateTime(it) }
    }
     private fun getDateTime(s: Long): String {

        return  "Updated at: " + SimpleDateFormat(
            "dd/MM/yyyy hh:mm a",
            Locale.ENGLISH
        ).format(Date(s * 1000))

    }

    fun getAddress():String
    {
        return name
    }



}