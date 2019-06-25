package com.example.climblogger.ui.route

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import kotlinx.android.synthetic.main.activity_add_route.*
import java.util.*

class AddRouteActivity : AppCompatActivity() {

    private lateinit var addRouteViewModel: AddRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

        addRouteButton.setOnClickListener { addRoute() }

        initKindSpinner()
    }

    private fun addRoute() {
        val commentText = commentTextInput.editText!!.text.toString()
        val linkText = linkTextInput.editText!!.text.toString()
        addRouteViewModel.insertRoute(
            Route(
                0,
                nameTextInput.editText!!.text.toString(),
                gradeTextInput.editText!!.text.toString(),
                kindSpinner.selectedItem as String,
                if (commentText.isEmpty()) null else commentText,
                if (linkText.isEmpty()) null else linkText,
                null, null,
                UUID.randomUUID().toString()
            )
        )
        finish()
    }

    private fun initKindSpinner() {
        val arrayAdapter =
            ArrayAdapter.createFromResource(this, R.array.route_kind, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kindSpinner.adapter = arrayAdapter
    }
}
