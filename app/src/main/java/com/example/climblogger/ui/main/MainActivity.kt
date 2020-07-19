package com.example.climblogger.ui.main

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.MenuListDialogFragment
import com.example.climblogger.R
import com.example.climblogger.SettingsDialogFragment
import com.example.climblogger.data.RouteRoomDatabase
import com.example.climblogger.dialogs.RadioButtonDialog
import com.example.climblogger.enums.RouteKind
import com.example.climblogger.fragments.*
import com.example.climblogger.ui.area.AddAreaActivity
import com.example.climblogger.ui.area.AreaActivity
import com.example.climblogger.ui.ascent.AddAscentActivity
import com.example.climblogger.ui.ascent.AscentActivity
import com.example.climblogger.ui.ascent.AscentActivity.Companion.EXTRA_ASCENT
import com.example.climblogger.ui.multipitch.MultipitchActivity
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import com.example.climblogger.ui.sector.AddSectorActivity
import com.example.climblogger.ui.sector.SectorActivity
import com.example.climblogger.ui.sector.SectorActivity.Companion.EXTRA_SECTOR
import com.example.climblogger.ui.stats.StatsActivity
import com.example.climblogger.util.backupDB
import com.example.climblogger.util.detachSwitch
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_picker.*


class MainActivity : AppCompatActivity(),
    RoutesFragment.OnFragmentInteractionListener,
    AscentsFragment.OnFragmentInteractionListener,
    SectorsFragment.OnFragmentInteractionListener,
    MultipitchesFragment.OnFragmentInteractionListener,
    AreasFragment.OnFragmentInteractionListener,
    MenuListDialogFragment.OnFragmentInteractionListener,
    SettingsDialogFragment.OnFragmentInteractionListener,
    RadioButtonDialog.OnRadioButtonSelectedListener {

    private lateinit var mainViewModel: MainViewModel

    private var showRadioPicker: RadioButtonDialog? = null
    private var bottomSheetMenu: MenuListDialogFragment = MenuListDialogFragment.newInstance()
    private var bottomSheetSettings: SettingsDialogFragment = SettingsDialogFragment.newInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        setContentView(R.layout.activity_main)
        setSupportActionBar(bar)

        initBottomNavigation()
        switchTo(mainViewModel.tabFragment)

        fab.setOnClickListener { floatingButtonClicked() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.bottom_bar_menu, menu)
        return true
    }

    private fun floatingButtonClicked() {
        when (mainViewModel.tabFragment.TAG) {
            RoutesFragment.TAG -> navigateTo(AddRouteActivity::class.java)
            AscentsFragment.TAG -> navigateTo(AddAscentActivity::class.java)
            SectorsFragment.TAG -> navigateTo(AddSectorActivity::class.java)
            AreasFragment.TAG -> navigateTo(AddAreaActivity::class.java)
        }
    }

    private fun <T : Any?> navigateTo(javaClass: Class<T>) {
        val intent = Intent(this, javaClass)
        startActivity(intent)
    }

    private fun openStatsActivity() {
        val intent = Intent(this, StatsActivity::class.java)
        startActivity(intent)
    }

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

    override fun onMultipitchClicked(multipitchId: String) {
        val intent = Intent(this, MultipitchActivity::class.java)
        intent.putExtra(MultipitchActivity.EXTRA_MULTIPITCH, multipitchId)
        startActivity(intent)
    }

    private fun initBottomNavigation() {
        bar.setNavigationOnClickListener {
            bottomSheetMenu.show(supportFragmentManager, MenuListDialogFragment.TAG)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.action_settings -> {
                bottomSheetSettings.show(
                    supportFragmentManager,
                    SettingsDialogFragment.TAG
                )
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun switchTo(tabFragment: MainActivityTabFragment) {
        detachSwitch(R.id.fragmentPlace, mainViewModel.tabFragment.TAG, tabFragment)
        mainViewModel.tabFragment = tabFragment

        if (tabFragment.TAG == AreasFragment.TAG) fab.show() else fab.hide()
        title_text_view.text = getString(tabFragment.title_id)
    }

    companion object {
        val TAG = MainActivity::class.qualifiedName
    }

    override fun onMenuItemClicked(menu_id: Int): Boolean {
        when (menu_id) {
            R.id.action_routes -> switchTo(RoutesFragment)
            R.id.action_areas -> switchTo(AreasFragment)
            R.id.action_ascents -> switchTo(AscentsFragment)
            R.id.action_sectors -> switchTo(SectorsFragment)
            R.id.action_multipitches -> switchTo(MultipitchesFragment)
            R.id.action_stats -> openStatsActivity()
        }
        bottomSheetMenu.dismiss()

        return true
    }

    private fun export() {
        backupDB(
            RouteRoomDatabase.getDatabase(applicationContext),
            getDatabasePath(RouteRoomDatabase.DBNAME)
        )
    }

    private fun showRouteShowDialog() {
        if (showRadioPicker == null) {
            Log.d(TAG, "Made the dialog")
            this@MainActivity.showRadioPicker = RadioButtonDialog(this@MainActivity)
        }
        showRadioPicker?.listener = this
        showRadioPicker?.show()
    }

    override fun onSettingsItemClicked(menu_id: Int): Boolean {
        when (menu_id) {
            R.id.action_export -> export()
            R.id.action_show -> showRouteShowDialog()
        }
        bottomSheetSettings.dismiss()
        return true
    }

    override fun onRadioButtonSelected(kind: RouteKind) {
        showRadioPicker?.dismiss()
        if (mainViewModel.tabFragment.TAG == RoutesFragment.TAG) {
            val r: RoutesFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentPlace) as RoutesFragment
            r.observeRouteData(kind)
        }
    }
}
