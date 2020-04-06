package com.example.climblogger.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var editRouteViewModel: ModifyRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        // unpack bundle
        var routeId: String = UUID.randomUUID().toString()
        intent.extras?.let {
            routeId = it.getString(EXTRA_ROUTE_ID, UUID.randomUUID().toString())
        }

        editRouteViewModel = ViewModelProviders.of(this).get(ModifyRouteViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(RouteFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                RouteFormFragment.newInstance(routeId),
                RouteFormFragment.TAG
            )

        }

        confirmationButton.setOnClickListener { editRoute() }
        confirmationButton.text = resources.getString(R.string.edit_route)
    }

    private fun editRoute() {
        editRouteViewModel.editRoute(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as RouteFormFragment).createRoute()
        )
        finish()
    }

    companion object {
        const val EXTRA_ROUTE_ID = "ROUTE_ID_EDIT_ROUTE_ACTIVITY"
    }

}
