package com.envilabindonesia.android.util

import android.content.Context
import com.envilabindonesia.android.R
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by galihadityo on 2019-03-27.
 */

object TimeUtil {

    fun convertMinuteSeconds(milis: Long): String {
        val date = Date(milis)
        val format = SimpleDateFormat("mm:ss", Locale("id"))
        return format.format(date)
    }

    fun getHumanUTC(convertedDate: String): String {
        var result = "Today"

        try {
            val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale("id"))
            input.timeZone = TimeZone.getTimeZone("GMT")

            val date = input.parse(convertedDate)
            val output = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale("id"))
            result = output.format(date)

        } catch (e: Exception) {
            e.printStackTrace()
        }


        return result
    }

    fun getMonthYear(prev: Int): String {
        val sdf = SimpleDateFormat("yyyy-MM", Locale("id"))
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, prev)
        return sdf.format(calendar.time)
    }

    fun getMonth(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("MM", Locale("id"))
        return sdf.format(calendar.time) ?: "1"
    }

    fun getYear(): String {
        val calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("yyyy", Locale("id"))
        return sdf.format(calendar.time) ?: "9999"
    }

    fun getHumanDay(context: Context, strDate: String): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale("id"))
        val date = sdf.parse(strDate)
        val calendar = Calendar.getInstance()
        calendar.time = date
        return context.getString(
            when (calendar.get(Calendar.DAY_OF_WEEK)) {
                1 -> R.string.day_sunday
                2 -> R.string.day_monday
                3 -> R.string.day_tuesday
                4 -> R.string.day_wednesday
                5 -> R.string.day_thursday
                6 -> R.string.day_friday
                else -> R.string.day_saturday
            }
        )
    }

    fun getHumanMonth(context: Context, month: Int): String {
        return context.getString(
            when (month) {
                1 -> R.string.month_jan
                2 -> R.string.month_feb
                3 -> R.string.month_mar
                4 -> R.string.month_apr
                5 -> R.string.month_may
                6 -> R.string.month_jun
                7 -> R.string.month_jul
                8 -> R.string.month_aug
                9 -> R.string.month_sep
                10 -> R.string.month_oct
                11 -> R.string.month_nov
                else -> R.string.month_dec
            }
        )
    }

    fun getHumanMonthYYYYMMDD(context: Context, strDate: String): String {
        val token = StringTokenizer(strDate, "-")
        val year = token.nextToken()
        val month = token.nextToken().toInt()
        return context.getString(
            when (month) {
                1 -> R.string.month_jan
                2 -> R.string.month_feb
                3 -> R.string.month_mar
                4 -> R.string.month_apr
                5 -> R.string.month_may
                6 -> R.string.month_jun
                7 -> R.string.month_jul
                8 -> R.string.month_aug
                9 -> R.string.month_sep
                10 -> R.string.month_oct
                11 -> R.string.month_nov
                else -> R.string.month_dec
            }
        )
    }

}