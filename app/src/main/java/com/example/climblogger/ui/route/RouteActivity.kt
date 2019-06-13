package com.example.climblogger.ui.route

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.ui.main.MainActivity.Companion.EXTRA_ROUTE
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.activity_route.view.*

class RouteActivity : AppCompatActivity() {

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        // get route from intent
        val route_id = intent.extras?.get(EXTRA_ROUTE) as Int

        routeViewModel = ViewModelProviders.of(this, RouteViewModelFactory(this.application, route_id))
            .get(RouteViewModel::class.java)

        routeViewModel.route.observe(this, Observer { setRouteViews(it) })
        routeViewModel.routeAscents.observe(this, Observer {
            Log.i("AKJDKJSA", it.toString())
        })


    }

    private fun setRouteViews(route: Route) {
        name.text = route.name
        grade.text = route.grade
        comment.text = route.comment
        kind.text = route.kind
    }

}
