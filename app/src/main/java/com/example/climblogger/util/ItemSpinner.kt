package com.example.climblogger.util

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

/**
 * Class used to get data from spinner so it's possible to select an item without to much fuss
 * Big differnce here is that we specify the adapter has to be an arrayadapter
 */
class ItemSpinner<T>(context: Context, attributeSet: AttributeSet) :
    Spinner(context, attributeSet) {

    var adapter: ArrayAdapter<T>? = null

    /*
        tag indicates what the last position was that's been set trough [selectItemInSpinner]
        if this tag isn't the same as the selected item in the listener than it means a manual selection
        has been made
     */
    private var tag: Int = 0

    fun setData(items: List<T>) {
        val arrayAdapter = ArrayAdapter(this.context, android.R.layout.simple_spinner_item, items)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        adapter = arrayAdapter
        super.setAdapter(adapter)
    }


    fun selectItemInSpinner(item: T?) {
        item?.let {
            val position = adapter?.getPosition(item)
            position?.let {
                tag = it
                super.setSelection(it)
            }
        }
    }

    fun onItemChosen(onItemSelected: (Int) -> Unit) {
        onItemSelectedListener = (object : OnItemSelectedListener {
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (tag != p2) {
                    onItemSelected.invoke(p2)
                }
            }
        })
    }
}

