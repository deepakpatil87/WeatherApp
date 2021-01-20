package com.hexaware.weatherapp.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.util.Constant
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        For temperature in Fahrenheit use units=imperial-->
//        <!--    For temperature in Celsius use units=metric-->

        val isCelsius = Constant.getBooleanSharePreferences(
            "CELSIUS"
        )
        swiCelsius.isChecked = isCelsius

        val isFahrenheit = Constant.getBooleanSharePreferences(
            "FAHRENHEIT"
        )
        swiFahrenheit.isChecked = isFahrenheit


        swiCelsius.setOnClickListener(View.OnClickListener {
            Constant.setBooleanSharePreference(
                "CELSIUS",
                swiCelsius.isChecked
            )
            Constant.setBooleanSharePreference(
                "FAHRENHEIT",
                false
            )
            swiFahrenheit.isChecked=false
        })

        swiFahrenheit.setOnClickListener(View.OnClickListener {

            Constant.setBooleanSharePreference(
                "CELSIUS",
                false
            )
            Constant.setBooleanSharePreference(
                "FAHRENHEIT",
                swiFahrenheit.isChecked
            )
            swiCelsius.isChecked=false
        })

        swiLocationReset.setOnClickListener(View.OnClickListener {

            Constant.setBooleanSharePreference(
                "LOCATION_RESET",
                swiLocationReset.isChecked
            )
        })
    }
}