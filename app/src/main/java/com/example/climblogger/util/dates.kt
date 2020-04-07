package com.example.climblogger.util

import java.util.*


/**
 * Pass nothing to get todays date
 */
fun getStringDate(
    day: Int = -1,
    month: Int = -1,
    year: Int = -1
): String {
    var newYear: Int = year
    var newMonth = month + 1
    var newDay = day
    if (day == -1) {
        val c = Calendar.getInstance()
        newYear = c.get(Calendar.YEAR)
        newMonth = c.get(Calendar.MONTH) + 1
        newDay = c.get(Calendar.DAY_OF_MONTH)
    }
    return "$newYear-${newMonth.toString().padStart(2, '0')}-${newDay.toString().padStart(2, '0')}"
}

