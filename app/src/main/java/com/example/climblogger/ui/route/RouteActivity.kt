package com.example.climblogger.ui.route

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.ui.main.MainActivity.Companion.EXTRA_ROUTE
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.activity_route.view.*

class RouteActivity : AppCompatActivity() {

    private lateinit var route: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        // get route from intent
        route = intent.extras?.get(EXTRA_ROUTE) as Route

        name.text = route.name
        grade.text = route.grade
        comment.text = route.comment
        kind.text = route.kind
    }

}
