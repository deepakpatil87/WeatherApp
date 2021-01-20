package com.hexaware.weatherapp.model.forecast

import android.os.Parcel
import android.os.Parcelable

data class Main(
    val feels_like: Double,
    val grnd_level: Int,
    val humidity: Int,
    val pressure: Int,
    val sea_level: Int,
    val temp: Double,
    val temp_kf: Double,
    val temp_max: Double,
    val temp_min: Double
):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble()
    ) {
    }

    fun getTempString(): String {
        return temp.toString().substringBefore(".") + "°"
    }

    fun getHumidityString(): String {
        return "Humidity: "+humidity.toString() + "°"
    }

    fun getTempMinString(): String {
        return temp_min.toString().substringBefore(".") + "°"
    }

    fun getTempMaxString(): String {
        return temp_max.toString().substringBefore(".") + "°"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeDouble(feels_like)
        parcel.writeInt(grnd_level)
        parcel.writeInt(humidity)
        parcel.writeInt(pressure)
        parcel.writeInt(sea_level)
        parcel.writeDouble(temp)
        parcel.writeDouble(temp_kf)
        parcel.writeDouble(temp_max)
        parcel.writeDouble(temp_min)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Main> {
        override fun createFromParcel(parcel: Parcel): Main {
            return Main(parcel)
        }

        override fun newArray(size: Int): Array<Main?> {
            return arrayOfNulls(size)
        }
    }
}