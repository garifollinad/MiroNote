package com.example.mironote.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    val ISO_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

    fun convertIsoToDate(dateIso: String?): Date {
        val format = SimpleDateFormat(ISO_FORMAT, Locale.getDefault())
        val tz = TimeZone.getTimeZone("UTC")
        format.timeZone = tz
        return format.parse(dateIso)
    }

}