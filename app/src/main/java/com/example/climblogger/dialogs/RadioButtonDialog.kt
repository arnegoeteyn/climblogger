package com.example.climblogger.dialogs

import android.app.Dialog
import android.content.Context
import com.example.climblogger.R
import com.example.climblogger.enums.RouteKind
import kotlinx.android.synthetic.main.dialog_picker.*

class RadioButtonDialog(context: Context) : Dialog(context) {

    var listener: OnRadioButtonSelectedListener? = null

    init {
        this.setContentView(R.layout.dialog_picker)
        this.confirmation_button.setOnClickListener {
            when (radio_group.checkedRadioButtonId) {
                radio_button_all.id -> callListener(RouteKind.ALL)
                radio_button_boulder.id -> callListener(RouteKind.BOULDER)
                radio_button_sport.id -> callListener(RouteKind.SPORT)
            }
        }
    }

    private fun callListener(kind: RouteKind) {
        listener?.onRadioButtonSelected(kind)
    }


    interface OnRadioButtonSelectedListener {
        fun onRadioButtonSelected(kind: RouteKind)
    }


}