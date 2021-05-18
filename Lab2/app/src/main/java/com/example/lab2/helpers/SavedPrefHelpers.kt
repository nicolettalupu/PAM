package com.example.lab2.helpers

import android.content.Context
import androidx.preference.PreferenceManager

fun Context.getSharedPref(key: String, isBoolean: Boolean = false, defaultValue: Boolean = false): Any? {
    val preferences = PreferenceManager.getDefaultSharedPreferences(this)
    if (isBoolean) {
        return preferences.getBoolean(key, defaultValue)
    }

    return preferences.getString(key, null)
}