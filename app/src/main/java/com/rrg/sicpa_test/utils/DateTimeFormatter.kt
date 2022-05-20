package com.rrg.sicpa_test.utils

import java.text.DateFormat
import java.util.*

class DateTimeFormatter {

    private val dateFormat: DateFormat by lazy {
        DateFormat.getDateInstance(DateFormat.DEFAULT)
    }

    fun getFormattedDate(date: Date?): String {
        date?.let {
            return dateFormat.format(it)
        } ?: return "-"
    }
}