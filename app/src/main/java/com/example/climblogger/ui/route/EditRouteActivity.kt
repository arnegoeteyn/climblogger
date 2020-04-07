package com.example.climblogger.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var editRouteViewModel: ModifyRouteViewModel

    private lateinit var routeId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)


        intent.extras?.let { extras ->
            extras.getString(EXTRA_ROUTE_ID)?.let { routeId ->
                this.routeId = routeId
            } ?: run { finish(); return }
        } ?: run { finish(); return }

        editRouteViewModel =
            ViewModelProviders.of(this, ModifyRouteViewModelFactory(application, null, routeId))
                .get(ModifyRouteViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(RouteFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                RouteFormFragment.newInstance(),
                RouteFormFragment.TAG
            )

        }

        confirmationButton.setOnClickListener { editRoute() }
        confirmationButton.text = resources.getString(R.string.edit_route)
    }

    private fun editRoute() {
        editRouteViewModel.updateRoute()
        finish()
    }

    companion object {
        const val EXTRA_ROUTE_ID = "ROUTE_ID_EDIT_ROUTE_ACTIVITY"
    }

}
