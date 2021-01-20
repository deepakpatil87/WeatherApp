package com.hexaware.weatherapp.util

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.Window
import android.view.WindowManager
import com.hexaware.weatherapp.R
import com.hexaware.weatherapp.WeatherApplication

class Constant {
    companion object {
        private const val PREF_NAME = "DQT_GPS_PREFERENCE_FILE"
        const val MSG_NETWORK = "Turn on mobile data or connect to Wi-Fi to access this feature"
        const val API_KEY = "fae7190d7e6433ec3a45285ffcf55c86"
        const val MS_TO_KMH = 3.6
        const val BASE_URL="http://api.openweathermap.org/"

        //************************* Boolean Shared Preferences

        fun setBooleanSharePreference(key: String, value: Boolean) {
            val sharedPref = WeatherApplication.getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            ) ?: return
            with(sharedPref.edit()) {
                putBoolean(key, value)
                commit()
            }
        }

        fun getBooleanSharePreferences(key: String): Boolean {
            val sharedPref = WeatherApplication.getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            return sharedPref.getBoolean(key, false)
        }

        fun clearSharePreference() {
            val sharedPref = WeatherApplication.getContext().getSharedPreferences(
                PREF_NAME,
                Context.MODE_PRIVATE
            )
            sharedPref?.edit()?.clear()?.commit()

        }


        fun progressDialog(context: Context): Dialog {
            val dialog = Dialog(context)
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setCancelable(false)

            val inflate = LayoutInflater.from(context).inflate(
                R.layout.progress_dialog_layout,
                null
            )
            dialog.setContentView(inflate)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
            dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#4D000000")))

            return dialog
        }

        fun getWindInKmh(wind: String): String {
            return String.format("%.0f", (wind.toDouble() * MS_TO_KMH))
        }
    }

}