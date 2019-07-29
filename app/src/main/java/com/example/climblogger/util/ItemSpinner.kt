package com.example.climblogger.util

import android.content.Context
import android.util.AttributeSet
import android.widget.ArrayAdapter
import android.widget.Spinner

/**
 * Class used to get data from spinner so it's possible to select an item without to much fuss
 */
class ItemSpinner<T>(context: Context, attributeSet: AttributeSet) : Spinner(context, attributeSet) {

    var adapter: ArrayAdapter<T>? = null

    fun setData(items: List<T>) {
        val arrayAdapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
        super.setAdapter(adapter)
    }


    fun selectItemInSpinner(item: T) {
        adapter?.getPosition(item)?.let { setSelection(it) }
    }
}