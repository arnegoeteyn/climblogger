package com.example.climblogger.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import java.util.*

class EditRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var editRouteViewModel: EditRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_route)

        editRouteViewModel = ViewModelProviders.of(this).get(EditRouteViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, RouteFormFragment.newInstance(UUID.randomUUID().toString()))
            .commit()
    }

    private fun editRoute() {
        editRouteViewModel.editRoute(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as RouteFormFragment).createRoute()
        )
        finish()
    }


}
