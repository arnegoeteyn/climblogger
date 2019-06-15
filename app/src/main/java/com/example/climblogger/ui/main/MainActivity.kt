package com.example.climblogger.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.climblogger.R
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.util.inTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    RoutesFragment.OnFragmentInteractionListener,
    AscentsFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()

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

    private fun initBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_routes -> switchToRoutes()
                R.id.action_ascents -> switchToAscents()
                else -> switchToRoutes()
            }
            true
        }
    }

    private fun switchToRoutes() {
        supportFragmentManager.inTransaction {
            replace(
                R.id.fragmentPlace,
                supportFragmentManager.findFragmentByTag(RoutesFragment.TAG) ?: RoutesFragment.newInstance(),
                RoutesFragment.TAG
            )
                .addToBackStack(RoutesFragment.TAG)
        }
    }

    private fun switchToAscents() {
        supportFragmentManager.inTransaction {
            replace(
                R.id.fragmentPlace,
                supportFragmentManager.findFragmentByTag(AscentsFragment.TAG) ?: AscentsFragment.newInstance(),
                RoutesFragment.TAG
            )
                .addToBackStack(RoutesFragment.TAG)
        }
    }

    companion object {
        public const val EXTRA_ROUTE = "EXTRA_ROUTE"
        public val TAG = MainActivity::class.qualifiedName

    }
}
