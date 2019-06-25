package com.example.climblogger.ui.route

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.setSpinnerData
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

        addRouteViewModel.allSectors.observe(this, Observer { sectors ->
            sectorSpinner.setSpinnerData(sectors)
        })
    }

    private fun addRoute() {
        val commentText = commentTextInput.editText!!.text.toString()
        val linkText = linkTextInput.editText!!.text.toString()
        val route = Route(
            (sectorSpinner.selectedItem as Sector).sectorId,
            nameTextInput.editText!!.text.toString(),
            gradeTextInput.editText!!.text.toString(),
            kindSpinner.selectedItem as String,
            if (commentText.isEmpty()) null else commentText,
            if (linkText.isEmpty()) null else linkText,
            null, null,
            UUID.randomUUID().toString()
        )
        addRouteViewModel.insertRoute(
            route
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
