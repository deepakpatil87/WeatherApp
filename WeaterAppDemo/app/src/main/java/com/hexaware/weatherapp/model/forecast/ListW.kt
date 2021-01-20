package com.hexaware.weatherapp.model.forecast

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

@Parcelize
data class ListW(

    val clouds:  @RawValue Clouds,
    val dt:  @RawValue Long,
    val dt_txt: @RawValue String,
    val main: @RawValue Main,
    val pop:  @RawValue Int,
    val sys:  @RawValue Sys,
    val visibility: @RawValue  Int,
    val weather:  @RawValue List<Weather>,
    val wind:  @RawValue Wind
):Parcelable{
    fun getDay(): String? {
        return dt.let { getDateTime(it)?.getDisplayName(TextStyle.FULL, Locale.getDefault()) }
    }

    private fun getDateTime(s: Long): DayOfWeek? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy")
            val netDate = Date(s * 1000)
            val formattedDate = sdf.format(netDate)

            LocalDate.of(
                formattedDate.substringAfterLast("/").toInt(),
                formattedDate.substringAfter("/").take(2).toInt(),
                formattedDate.substringBefore("/").toInt()
            )
                .dayOfWeek
        } catch (e: Exception) {
            e.printStackTrace()
            DayOfWeek.MONDAY
        }
    }
}