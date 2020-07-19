package com.example.climblogger.util

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.app.AlertDialog


class ConfirmationButton(context: Context, attrs: AttributeSet) :
    androidx.appcompat.widget.AppCompatButton(context, attrs) {

    private var onPositiveClick: (() -> Unit)? = null


    override fun performClick(): Boolean {
        showConfirmationDialog()
        return super.performClick()
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("CONFIRMATION")
        builder.setMessage("Do you want to delete?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            onPositiveClick?.invoke()
        }
        builder.setNegativeButton("No") { dialog, _ -> dialog.dismiss() }
        val alert = builder.create()
        alert.show()
    }

    fun setOnPositiveClickListener(onPositiveClickListener: () -> Unit) {
        this.onPositiveClick = onPositiveClickListener
    }

}