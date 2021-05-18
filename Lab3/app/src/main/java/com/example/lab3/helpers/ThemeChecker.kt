package com.example.lab3.helpers

import android.content.Context
import com.example.lab3.R

class ThemeChecker {
    fun darkModeChecker(c: Context) {
        if (c.getSharedPref("DARK_MODE_ON", true) as Boolean) {
            c.setTheme(R.style.AppThemeDark)
        }
    }

    fun isDarkModeOn(c: Context): Boolean {
        return c.getSharedPref("DARK_MODE_ON", true) as Boolean
    }
}