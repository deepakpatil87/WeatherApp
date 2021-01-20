package com.hexaware.weatherapp.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.model.forecast.ListW
import com.hexaware.weatherapp.util.Constant
import com.hexaware.weatherapp.viewmodel.WeatherDetailViewModel
import com.pivincii.livedata_retrofit.network.ApiErrorResponse
import com.pivincii.livedata_retrofit.network.ApiSuccessResponse
import customer.kotlin.com.kotlincustomerapp.util.NetworkUtil
import kotlinx.android.synthetic.main.fragment_city_weather_detail.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class CityWeatherDetailsFragment : Fragment() {

    private lateinit var weatherDetailViewModel: WeatherDetailViewModel
    private lateinit var progressBarDialog: Dialog
    private lateinit var mContext: Context


    private lateinit var forecastAdapter: ForecastAdapter
    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_city_weather_detail, container, false)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        progressBarDialog = Constant.progressDialog(mContext)
        weatherDetailViewModel =
            ViewModelProvider(
                this@CityWeatherDetailsFragment
            ).get(WeatherDetailViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        // val layoutManager = GridLayoutManager(mContext, 2)

        recyclerForecast.layoutManager = layoutManager
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recyclerForecast.layoutManager = layoutManager
        recyclerForecast.hasFixedSize()

        val lat = requireArguments().getDouble("LAT")
        val lon = requireArguments().getDouble("LON")

        val unitCelsius = Constant.getBooleanSharePreferences("CELSIUS")
        val unit: String
        if (unitCelsius) {
            //  For temperature in Fahrenheit use units=imperial
            //   For temperature in Celsius use units=metric
            unit = "metric"
        } else {
            unit = "imperial"
        }

        if (NetworkUtil.isConnectedOrConnecting(mContext)) {
            getCurrentWeatherDetail(lat, lon, unit)

            getForecastWeatherDetail(lat, lon, unit)

            nextAndCurrentWeekDays()
        } else {
            Toast.makeText(mContext, Constant.MSG_NETWORK, Toast.LENGTH_LONG).show()
        }

    }

    private fun getCurrentWeatherDetail(lat: Double, lon: Double, unit: String) {


        progressBarDialog.show()

        weatherDetailViewModel.callCurrentWeatherAPI(lat, lon, unit, Constant.API_KEY)
            .observe(
                viewLifecycleOwner,
                { getWeatherRes ->

                    when (getWeatherRes) {
                        is ApiSuccessResponse -> {
                            val weatherRes = getWeatherRes.body


                            val addressStr = weatherRes.name + " , " + weatherRes.sys.country
                            address.text = addressStr

                            val updatedAt: Long = weatherRes.dt
                            val updatedAtText = "Updated at: " + SimpleDateFormat(
                                "dd/MM/yyyy hh:mm a",
                                Locale.ENGLISH
                            ).format(Date(updatedAt * 1000))
                            updated_at.text = updatedAtText
                            txtStatus.text =
                                weatherRes.weather[0].description.toUpperCase(Locale.ROOT)
                            var isCelsius = Constant.getBooleanSharePreferences("CELSIUS")
                            val tempStr: String
                            val tempMin: String
                            val tempMax: String
                            if (isCelsius) {
                                tempStr = weatherRes.main.temp.toString() + "°C"
                                tempMin =
                                    "Min Temp: " + weatherRes.main.temp.toString() + "°C"
                                tempMax =
                                    "Max Temp: " + weatherRes.main.temp.toString() + "°C"
                            } else {
                                tempStr = weatherRes.main.temp.toString() + "°F"
                                tempMin =
                                    "Min Temp: " + weatherRes.main.temp.toString() + "°F"
                                tempMax =
                                    "Max Temp: " + weatherRes.main.temp.toString() + "°F"
                            }

                            txtTemp.text = tempStr
                            txtTemp_min.text = tempMin
                            txtTemp_max.text = tempMax
                            txtWind.text = getString(
                                R.string.temp_wind,
                                Constant.getWindInKmh(weatherRes.wind.speed.toString())
                            )


                            txtHumidity.text = weatherRes.main.humidity.toString()
                            progressBarDialog.dismiss()
                        }
                        is ApiErrorResponse -> {

                            progressBarDialog.dismiss()

                        }
                        else -> {

                            progressBarDialog.dismiss()
                        }
                    }
                })
    }

    private fun getForecastWeatherDetail(lat: Double, lon: Double, unit: String) {

        progressBarDialog.show()

        weatherDetailViewModel.callForecastWeatherAPI(lat, lon, unit, Constant.API_KEY)
            .observe(
                viewLifecycleOwner,
                { forecast ->

                    when (forecast) {
                        is ApiSuccessResponse -> {
                            val weatherRes = forecast.body
                            val weatheList = weatherRes.list

                            var i = 0
                            val filterList = mutableListOf<ListW>()

                            for (item in weatheList) {

                                if ((item.dt_txt).contains((weeklyDateList[i] + " 12:00:00")) && i != 7) {
                                    filterList.add(item)
                                    i += 1

                                }
                            }

                            forecastAdapter = ForecastAdapter(filterList)
                            recyclerForecast.adapter = forecastAdapter
                            progressBarDialog.dismiss()
                        }
                        is ApiErrorResponse -> {

                            progressBarDialog.dismiss()

                        }
                        else -> {

                            progressBarDialog.dismiss()
                        }
                    }
                })
    }

    private var weeklyDateList = mutableListOf<String>()

    @SuppressLint("SimpleDateFormat")
    private fun nextAndCurrentWeekDays() {
        // Get calendar set to current date and time
        val calender = Calendar.getInstance()


        val weeklyDate: DateFormat = SimpleDateFormat("YYYY-MM-DD")

        calender.add(Calendar.DATE, 0)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))
        calender.add(Calendar.DATE, 1)
        weeklyDateList.add(weeklyDate.format(calender.time))

    }
}