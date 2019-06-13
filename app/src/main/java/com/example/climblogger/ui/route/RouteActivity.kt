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

    private lateinit var route: Route
    private lateinit var ascents: List<Ascent>

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        // get route from intent
        route = intent.extras?.get(EXTRA_ROUTE) as Route

        routeViewModel = ViewModelProviders.of(this).get(RouteViewModel::class.java)

        routeViewModel.loadAscentsFromRoute(route.route_id).observe(this, Observer {
            Log.i("AKJDKJSA", it.toString())
        })


        name.text = route.name
        grade.text = route.grade
        comment.text = route.comment
        kind.text = route.kind


    }

}
