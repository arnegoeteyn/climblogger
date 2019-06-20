package com.example.climblogger.ui.route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import kotlinx.android.synthetic.main.activity_add_route.*
import kotlinx.android.synthetic.main.activity_route.*

class AddRouteActivity : AppCompatActivity() {

    private lateinit var addRouteViewModel: AddRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

        addRouteButton.setOnClickListener { addRoute() }
    }

    private fun addRoute() {
        addRouteViewModel.insertRoute(
            Route(
                0, nameTextInput.editText!!.text.toString(),
                "8a", "sport", null, null,
                null, null
            )
        )
        finish()
    }
}
