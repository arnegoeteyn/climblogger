package com.example.climblogger.ui.sector

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.RouteWithAscentsAdapter
import com.example.climblogger.data.RouteWithAscents
import com.example.climblogger.data.SectorWithArea
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.standardInit
import kotlinx.android.synthetic.main.activity_sector.*

class SectorActivity : AppCompatActivity() {

    private lateinit var sectorViewModel: SectorViewModel
    private lateinit var routeAdapter: LiveDataAdapter<RouteWithAscents>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sector)

        val sectorId = intent.extras?.get(EXTRA_SECTOR) as String
        sectorViewModel = ViewModelProviders.of(this, SectorViewModelFactory(application, sectorId))
            .get(SectorViewModel::class.java)

        initRoutesRecyclerView()

        sectorViewModel.sectorWithArea.observe(this, Observer {
            it?.let { sector: SectorWithArea ->
                updateSectorUI(sector)
                delete_button.setOnPositiveClickListener { deleteSector(sector) }
            }
        })

        edit_button.setOnClickListener { editSector(sectorId) }
        add_route_button.setOnClickListener { addRoute(sectorId) }

    }

    private fun editSector(sector_id: String) {
        intent = Intent(this, EditSectorActivity::class.java)
        val bundle = Bundle()
        bundle.putString(EditSectorActivity.EXTRA_SECTOR_ID, sector_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun updateSectorUI(sector: SectorWithArea) {
        nameText.text = sector.sector.name
        commentText.text = sector.sector.comment
        areaText.text = sector.area_name
    }

    private fun initRoutesRecyclerView() {
        routeAdapter = RouteWithAscentsAdapter()
        routesRecyclerView.standardInit(routeAdapter)

        // clicking brings you to the routedetail
        routesRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                sectorViewModel.sectorRoutes.value?.get(position)
                    ?.let { onRouteClicked(it.route_id) }
            }
        })

        // loading the data
        sectorViewModel.sectorRoutes.observe(this, Observer { routes ->
            routeAdapter.setData(routes)
        })
    }

    private fun deleteSector(sector: SectorWithArea) {
        sectorViewModel.deleteSector(sector.sector)
        finish()
    }

    private fun onRouteClicked(route_id: String) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(RouteActivity.EXTRA_ROUTE, route_id)
        startActivity(intent)
    }

    /**
     * Add a new route
     * @param sector_id Which sector the route should be part of
     */
    private fun addRoute(sector_id: String) {
        intent = Intent(this, AddRouteActivity::class.java)
        intent.putExtra(AddRouteActivity.EXTRA_SECTOR_ID, sector_id)
        startActivity(intent)
    }


    companion object {
        const val EXTRA_SECTOR = "EXTRA_SECTOR"
    }
}
