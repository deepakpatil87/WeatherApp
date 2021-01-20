package com.hexaware.weatherapp.model.current

import android.annotation.SuppressLint
import android.os.Parcel
import android.os.Parcelable
import com.hexaware.weatherapp.util.Constant

@SuppressLint("ParcelCreator")
data class Wind(
    val deg: Int,
    val speed: Double
): Parcelable {
    fun getSpeed(): String? {
        return Constant.getWindInKmh(speed.toString()) + "km/h"

    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        TODO("Not yet implemented")
    }
}