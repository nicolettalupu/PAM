package com.example.lab3.helpers

import android.content.Context
import android.graphics.Rect
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager

class KeyboardHelper {
    fun hideKeyboard(c: Context, v: View) {
        val imm = c.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        if (isKeyboardShown(v.rootView)) {
            imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0)
        }
    }

    fun isKeyboardShown(rootView: View): Boolean {
        val softKeyboardHeightDpThreshold = 128
        val r = Rect()
        rootView.getWindowVisibleDisplayFrame(r)
        val dm = rootView.resources.displayMetrics

        val heightDiff = rootView.bottom - r.bottom

        val isKeyboardShown = heightDiff > softKeyboardHeightDpThreshold * dm.density
        Log.d("KBD", "isKeyboardShown ? " + isKeyboardShown + ", heightDiff:" + heightDiff + ", density:" + dm.density + "root view height:" + rootView.height + ", rect:" + r)
        return isKeyboardShown
    }
}