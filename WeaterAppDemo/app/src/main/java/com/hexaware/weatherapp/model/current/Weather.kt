package com.hexaware.weatherapp.model.current

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable

@SuppressLint("ParcelCreator")
data class Weather(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
):Parcelable{
    fun getDescriptionText(): String? {
        return description?.capitalize()
    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}