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
    private lateinit var sectorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)


        intent.extras?.let { extras ->
            extras.getString(EXTRA_SECTOR_ID)?.let { ascentId ->
                sectorId = ascentId
            } ?: run { finish(); return }
        } ?: run { finish(); return }

        addRouteViewModel =
            ViewModelProviders.of(this, ModifyRouteViewModelFactory(application, sectorId, null))
                .get(ModifyRouteViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(RouteFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                RouteFormFragment.newInstance(),
                RouteFormFragment.TAG
            )
        }

        confirmationButton.setOnClickListener { addRoute() }
        confirmationButton.text = resources.getString(R.string.add_route)
    }

    private fun addRoute() {
        addRouteViewModel.insertRoute()
        finish()
    }


    companion object {
        const val EXTRA_SECTOR_ID =
            "EXTRA_SECTOR_ID" // pass route id to already select route in spinner
    }
}
