package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*

class AddAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var addAscentViewModel: ModifyAscentViewModel
    private lateinit var routeId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        intent.extras?.let { extras ->
            extras.get(EXTRA_ROUTE_ID)?.let { routeId ->
                this.routeId = routeId as String
            } ?: run { finish(); return }
        } ?: run { finish(); return }


        addAscentViewModel =
            ViewModelProviders.of(this, ModifyAscentViewModelFactory(application, routeId, null))
                .get(ModifyAscentViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(AscentFormFragment.TAG) {
            addAscentViewModel.ascentRouteId = routeId
            replace(R.id.fragmentPlace, AscentFormFragment.newInstance(), AscentFormFragment.TAG)
        }

        confirmationButton.setOnClickListener { addAscent() }
        confirmationButton.text = resources.getString(R.string.add_ascent)
    }

    private fun addAscent() {
        addAscentViewModel.insertAscent()
        finish()
    }

    companion object {
        const val EXTRA_ROUTE_ID =
            "EXTRA_ROUTE_ID" // route ID can be passed to already select the route
    }

}
