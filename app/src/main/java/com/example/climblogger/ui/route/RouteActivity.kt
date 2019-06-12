package com.example.climblogger.ui.route

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.ui.main.MainActivity.Companion.EXTRA_ROUTE

class RouteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        val route: Route = intent.extras?.get(EXTRA_ROUTE) as Route

        Log.i("ROUTE", route.toString())

    }
}
