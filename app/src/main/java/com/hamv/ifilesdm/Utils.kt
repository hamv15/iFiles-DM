package com.hamv.ifilesdm

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.view.inputmethod.InputMethodManager
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

fun Activity.toastMessage(message: String, length: Int = Toast.LENGTH_SHORT){
    Toast.makeText(this, message, length).show()
}

fun Activity.sbMessage(view: View, text: String, textColor: String? = "#FFFFFF", bgColor: String? = "#9E1734", length: Int = Snackbar.LENGTH_SHORT){
    Snackbar.make(view, text, length)
        .setTextColor(Color.parseColor(textColor))
        .setBackgroundTint(Color.parseColor(bgColor))
        .show()
}

fun View.hideKeyboard() {
    val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputManager.hideSoftInputFromWindow(windowToken, 0)
}
