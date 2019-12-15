package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.ui.route.RouteFormFragment
import kotlinx.android.synthetic.main.activity_add_ascent.*
import java.util.*

class AddAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var addAscentViewModel: AddAscentViewModel

    private var arrayAdapter: ArrayAdapter<Route>? = null

    private var route: Route? = null // route can be null if no route has been passed from intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ascent)

        var route_id = ""
        intent.extras?.let {
            route_id = intent.extras?.get(EXTRA_ROUTE_ID) as String
        }

        addAscentViewModel =
            ViewModelProviders.of(this, AddAscentViewModelFactory(this.application, route_id))
                .get(AddAscentViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentPlace,
                AscentFormFragment.newInstance(UUID.randomUUID().toString(), route_id)
            )
            .commit()


        addAscentButton.setOnClickListener { addAscent() }
    }

    private fun addAscent() {
        addAscentViewModel.insertAscent(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AscentFormFragment).createAscent()
        )
        finish()

    }


    companion object {
        const val EXTRA_ROUTE_ID =
            "EXTRA_ROUTE_ID" // route ID can be passed to already select the route
    }

}
