package com.hexaware.weatherapp.api

import com.beyondkey.dq.network.LiveDataCallAdapterFactory
import com.hexaware.weatherapp.util.Constant
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitClient {
    fun getRetrofit(): Retrofit {

        val logging = HttpLoggingInterceptor()
        // set your desired log level
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(2, TimeUnit.MINUTES)
        httpClient.connectTimeout(2, TimeUnit.MINUTES)
        httpClient.addInterceptor(logging) // <- this is the important line!

        return Retrofit.Builder()

            .baseUrl(Constant.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(LiveDataCallAdapterFactory())
            .client(httpClient.build())
            .build()
    }
}