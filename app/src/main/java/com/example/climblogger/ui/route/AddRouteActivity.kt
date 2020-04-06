package com.example.climblogger.ui.route

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var addRouteViewModel: ModifyRouteViewModel
    private var sectorId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        addRouteViewModel = ViewModelProviders.of(this).get(ModifyRouteViewModel::class.java)

        intent.extras?.let {
            sectorId = intent.extras?.get(EXTRA_SECTOR_ID) as String
        }

        supportFragmentManager.addIfNotAlreadythere(RouteFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                RouteFormFragment.newInstance(UUID.randomUUID().toString(), sectorId),
                RouteFormFragment.TAG
            )
        }

        confirmationButton.setOnClickListener { addRoute() }
        confirmationButton.text = resources.getString(R.string.add_route)
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
