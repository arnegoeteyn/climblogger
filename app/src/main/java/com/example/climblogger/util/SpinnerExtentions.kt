package com.example.climblogger.util

import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.climblogger.R

fun Spinner.setSpinnerData(items: List<Any>) {
    val arrayAdapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, items)
    arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = arrayAdapter
}
