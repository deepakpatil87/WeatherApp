package com.hexaware.weatherapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "location_table")
data class LocationTable(

    @ColumnInfo(name = "city_name")
    var cityName: String,

    @ColumnInfo(name = "state_name")
    var stateName: String,

    @ColumnInfo(name = "country_name")
    var countryName: String,

    @ColumnInfo(name = "address")
    var address: String,

    @ColumnInfo(name = "latitude")
    var latitude: Double,

    @ColumnInfo(name = "longitude")
    var longitude: Double,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var Id: Int? = null

)