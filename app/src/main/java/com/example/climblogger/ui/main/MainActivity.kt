package com.example.climblogger.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.ui.area.AddAreaActivity
import com.example.climblogger.ui.area.AreaActivity
import com.example.climblogger.ui.ascent.AddAscentActivity
import com.example.climblogger.ui.ascent.AscentActivity
import com.example.climblogger.ui.ascent.AscentActivity.Companion.EXTRA_ASCENT
import com.example.climblogger.ui.main.fragments.AreasFragment
import com.example.climblogger.ui.main.fragments.AscentsFragment
import com.example.climblogger.ui.main.fragments.RoutesFragment
import com.example.climblogger.ui.main.fragments.SectorsFragment
import com.example.climblogger.ui.multipitch.Multipitches
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import com.example.climblogger.ui.sector.AddSectorActivity
import com.example.climblogger.ui.sector.SectorActivity
import com.example.climblogger.ui.sector.SectorActivity.Companion.EXTRA_SECTOR
import com.example.climblogger.ui.stats.StatsActivity
import com.example.climblogger.util.addIfNotAlreadythere
import com.example.climblogger.util.detachSwitch
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    RoutesFragment.OnFragmentInteractionListener,
    AscentsFragment.OnFragmentInteractionListener,
    SectorsFragment.OnFragmentInteractionListener,
    AreasFragment.OnFragmentInteractionListener {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        initBottomNavigation()
        switchTo(mainViewModel.tabFragment)

        floatingActionButton.setOnClickListener { floatingButtonClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.action_stats -> {
                startActivity(Intent(this, StatsActivity::class.java))
                return true
            }
            R.id.action_multipitches -> {
                startActivity(Intent(this, Multipitches::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    /*
        Stuff for the floating plus button
        and launching new activities from this
     */
    private fun floatingButtonClicked() {
        when (mainViewModel.tabFragment.TAG) {
            RoutesFragment.TAG -> newRouteActivity()
            AscentsFragment.TAG -> newAscentActivity()
            SectorsFragment.TAG -> newSectorActivity()
            AreasFragment.TAG -> newAreaActivity()
        }
    }

    private fun newAreaActivity() {
        val intent = Intent(this, AddAreaActivity::class.java)
        startActivity(intent)
    }

    private fun newAscentActivity() {
        val intent = Intent(this, AddAscentActivity::class.java)
        startActivity(intent)
    }

    private fun newRouteActivity() {
        val intent = Intent(this, AddRouteActivity::class.java)
        startActivity(intent)
    }

    private fun newSectorActivity() {
        val intent = Intent(this, AddSectorActivity::class.java)
        startActivity(intent)
    }

    /*
      Handle what happens when an item gets selected
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

    override fun onSectorClicked(sector_id: String) {
        val intent = Intent(this, SectorActivity::class.java)
        intent.putExtra(EXTRA_SECTOR, sector_id)
        startActivity(intent)
    }

    override fun onAreaClicked(areaId: String) {
        val intent = Intent(this, AreaActivity::class.java)
        intent.putExtra(AreaActivity.EXTRA_AREA, areaId)
        startActivity(intent)
    }

    /*
        Stuff for bottom navigation
     */
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
        detachSwitch(R.id.fragmentPlace, mainViewModel.tabFragment.TAG, tabFragment)
        mainViewModel.tabFragment = tabFragment

        if (tabFragment.TAG == AreasFragment.TAG) floatingActionButton.show() else floatingActionButton.hide()
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
