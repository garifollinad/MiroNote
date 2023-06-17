package com.example.mironote.utils

import android.content.SharedPreferences
import javax.inject.Inject

class PrefUtils @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {

    companion object {
        const val TOKEN = "token"
    }

    fun saveData(key: String, value: Any?) {
        when (value) {
            is Int -> sharedPreferences.edit().putInt(key, value).apply()
            is Float -> sharedPreferences.edit().putFloat(key, value).apply()
            is Boolean -> sharedPreferences.edit().putBoolean(key, value).apply()
            is Long -> sharedPreferences.edit().putLong(key, value).apply()
            is String -> sharedPreferences.edit().putString(key, value).apply()
        }
    }


    fun getDataString(key: String) = sharedPreferences.getString(key, "") ?: ""

}