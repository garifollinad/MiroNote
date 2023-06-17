package com.example.mironote.utils

import android.content.Context
import com.example.mironote.R

class TimeUtil(val context: Context) {

    val SECOND_MILLIS = 1000
    val MINUTE_MILLIS = 60 * SECOND_MILLIS
    val HOUR_MILLIS = 60 * MINUTE_MILLIS
    val DAY_MILLIS = 24 * HOUR_MILLIS

    fun getTimeAgo(time: Long): String? {
        var time = time
        if (time < 1000000000000L) {
            time *= 1000
        }
        val now: Long = System.currentTimeMillis()
        var diff = now - time

        if (diff < 0) return null

        return if (diff < MINUTE_MILLIS) {
            context.getString(R.string.now)
        } else if (diff < 2 * MINUTE_MILLIS) {
            context.getString(R.string.a_minute_ago)
        } else if (diff < 50 * MINUTE_MILLIS) {
            diff /= MINUTE_MILLIS
            diff.toString() + " " + context.getString(R.string.minutes_ago)
        } else if (diff < 90 * MINUTE_MILLIS) {
            context.getString(R.string.an_hour_ago)
        } else if (diff < 24 * HOUR_MILLIS) {
            diff /= HOUR_MILLIS
            diff.toString() + " " + context.getString(R.string.hours_ago)
        } else if (diff < 48 * HOUR_MILLIS) {
            context.getString(R.string.yesterday)
        } else {
            diff /= DAY_MILLIS
            if (diff.toInt() == 1) {
                diff.toString() + " " + context.getString(R.string.day_ago)
            } else {
                diff.toString() + " " + context.getString(R.string.days_ago)
            }
        }
    }
}