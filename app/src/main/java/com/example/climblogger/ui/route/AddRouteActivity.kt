package com.example.climblogger.ui.route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_add_route.*
import java.util.*

class AddRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var addRouteViewModel: AddRouteViewModel
    private var sector_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        intent.extras?.let {
            // a sector_id has been passed along
            sector_id = intent.extras?.get(EXTRA_SECTOR_ID) as String
        }


        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentPlace,
                RouteFormFragment.newInstance(UUID.randomUUID().toString(), sector_id)
            )
            .commit()

        addRouteButton.setOnClickListener { addRoute() }

    }

    private fun addRoute() {
        addRouteViewModel.insertRoute(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as RouteFormFragment).createRoute()
        )
        finish()
    }


    companion object {
        const val EXTRA_SECTOR_ID =
            "EXTRA_SECTOR_ID" // pass route id to already select route in spinner
    }
}
