package com.example.billduassignment.database

import android.content.Context
import android.content.SharedPreferences

class MySharedPreferences(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(
        SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val SHARED_PREFERENCES_NAME = "MyPrefs"
        private const val LOCALE_KEY = "locale"
    }

    fun setLang(data: String) {
        val editor = sharedPreferences.edit()
        editor.putString(LOCALE_KEY, data)
        editor.apply()
    }

    fun getLang(): String? {
        return sharedPreferences.getString(LOCALE_KEY, "en")
    }
}