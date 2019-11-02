package com.example.climblogger.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_add_route.*
import kotlinx.android.synthetic.main.activity_edit_route.*
import kotlinx.android.synthetic.main.list_item_ascent.*
import java.util.*

class EditRouteActivity : AppCompatActivity(), RouteFormFragment.OnFragmentInteractionListener {

    private lateinit var editRouteViewModel: EditRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_route)



        // unpack bundle
        var route_id: String = UUID.randomUUID().toString()
        intent.extras?.let {
            route_id = it.getString(EXTRA_ROUTE_ID, UUID.randomUUID().toString())
        }

        editRouteViewModel = ViewModelProviders.of(this).get(EditRouteViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, RouteFormFragment.newInstance(route_id))
            .commit()

        editRouteButton.setOnClickListener { editRoute() }
    }

    private fun editRoute() {
        editRouteViewModel.editRoute(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as RouteFormFragment).createRoute()
        )
        finish()
    }

    companion object {
        public const val EXTRA_ROUTE_ID = "ROUTE_ID_EDIT_ROUTE_ACTIVITY"
    }

}
