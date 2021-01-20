package com.hexaware.weatherapp

import android.app.Application
import android.content.Context

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WeatherApplication : Application() {
    // No need to cancel this scope as it'll be torn down with the process
   // var applicationScope = CoroutineScope(SupervisorJob())

    companion object {
        lateinit var ctx: Context

        fun getContext(): Context
        {
            return ctx
        }

    }

    override fun onCreate() {
        super.onCreate()
        ctx = applicationContext
    }

}
