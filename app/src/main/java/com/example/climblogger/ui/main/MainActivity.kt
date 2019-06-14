package com.example.climblogger.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.climblogger.R
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.util.inTransaction

class MainActivity : AppCompatActivity(),
    RoutesFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchToRoutes()
    }

    /**
     * Handle what happens when a route gets selected
     */
    override fun onRouteClicked(route_id: Int) {
        val intent: Intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, route_id)
        startActivity(intent)
    }

    private fun switchToRoutes() {
        supportFragmentManager.inTransaction {
            add(R.id.fragmentPlace, RoutesFragment.newInstance(), RoutesFragment.TAG)
        }
    }

    companion object {
        public const val EXTRA_ROUTE = "EXTRA_ROUTE"
        public val TAG = MainActivity::class.qualifiedName

    }
}
