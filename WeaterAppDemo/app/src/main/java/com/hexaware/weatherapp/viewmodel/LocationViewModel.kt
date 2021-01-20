package com.hexaware.weatherapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.hexaware.weatherapp.model.LocationTable
import com.example.smslist.room.LocationRoomDatabase
import com.hexaware.weatherapp.WeatherApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class LocationViewModel : ViewModel() {
    private val mDb = LocationRoomDatabase.getDatabase(
        WeatherApplication.getContext(),
        CoroutineScope(SupervisorJob())
    )

    fun allLocation(): LiveData<List<LocationTable>> {
        return mDb.locationDao().getAllLocation().asLiveData()
    }
    suspend fun resetAllLocation() {
        return mDb.locationDao().deleteAll()
    }

}