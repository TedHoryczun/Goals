package com.goals.ted.goals

import java.text.SimpleDateFormat
import java.util.Calendar

/**
 * Created by ted on 9/20/16.
 */
object DateFormatter {

    fun convertDateToDateFormat(date: Calendar?): String {
        var formattedDate: String? = null
        val format1 = SimpleDateFormat("EEE, MMM dd yyyy")
        formattedDate = format1.format(date?.time)
        return formattedDate

    }
}
