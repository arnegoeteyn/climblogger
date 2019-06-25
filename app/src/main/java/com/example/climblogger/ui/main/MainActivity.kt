package com.example.climblogger.ui.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.climblogger.R
import com.example.climblogger.ui.ascent.AddAscentActivity
import com.example.climblogger.ui.ascent.AscentActivity
import com.example.climblogger.ui.ascent.AscentActivity.Companion.EXTRA_ASCENT
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import com.example.climblogger.util.inTransaction
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    RoutesFragment.OnFragmentInteractionListener,
    AscentsFragment.OnFragmentInteractionListener,
    SectorsFragment.OnFragmentInteractionListener,
    AreasFragment.OnFragmentInteractionListener {

    private var currentFragmentTag: String = RoutesFragment.TAG

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initBottomNavigation()

        switchToRoutes()

        floatingActionButton.setOnClickListener { floatingButtonClicked() }
    }

    private fun floatingButtonClicked() {
        when (currentFragmentTag) {
            RoutesFragment.TAG -> newRouteActivity()
            AscentsFragment.TAG -> newAscentActivity()
        }
    }

    private fun newAscentActivity() {
        val intent = Intent(this, AddAscentActivity::class.java)
        startActivity(intent)
    }

    private fun newRouteActivity() {
        val intent = Intent(this, AddRouteActivity::class.java)
        startActivity(intent)
    }

    /**
     * Handle what happens when a route gets selected
     */
    override fun onRouteClicked(route_id: String) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, route_id)
        startActivity(intent)

    }

    override fun onAscentClicked(ascent_id: String) {
        val intent = Intent(this, AscentActivity::class.java)
        intent.putExtra(EXTRA_ASCENT, ascent_id)
        startActivity(intent)
    }

    private fun initBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.action_routes -> switchToRoutes()
                R.id.action_ascents -> switchToAscents()
                R.id.action_sectors -> switchToSectors()
                R.id.action_areas -> switchToAreas()
            }
            true
        }
    }


    private fun switchTo(tabFragment: MainActivityTabFragment) {
        val currentFragment = supportFragmentManager.findFragmentByTag(currentFragmentTag)
        val newFragment = supportFragmentManager.findFragmentByTag(tabFragment.TAG)
        val fragment = newFragment ?: tabFragment.newInstance()
        supportFragmentManager.inTransaction {
            if (currentFragment != null) {
                detach(currentFragment)
                if (newFragment != null) {
                    attach(fragment)
                } else {
                    replace(R.id.fragmentPlace, fragment, tabFragment.TAG)
                }
            } else {
                // no fragment has been added yet
                replace(R.id.fragmentPlace, fragment, tabFragment.TAG)
            }
        }
        currentFragmentTag = tabFragment.TAG
    }

    private fun switchToRoutes() {
        switchTo(RoutesFragment.Companion)
    }

    private fun switchToAscents() {
        switchTo(AscentsFragment.Companion)
    }

    private fun switchToSectors() {
        switchTo(SectorsFragment.Companion)
    }

    private fun switchToAreas() {
        switchTo(AreasFragment.Companion)
    }

    companion object {
        public val TAG = MainActivity::class.qualifiedName

    }
}
