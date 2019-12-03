package com.example.climblogger.ui.sector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.AddRouteActivity.Companion.EXTRA_SECTOR_ID
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.activity_sector.*
import kotlinx.android.synthetic.main.route_list_item.view.*

class SectorActivity : AppCompatActivity() {

    private lateinit var sectorViewModel: SectorViewModel

    private lateinit var sector: Sector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sector)

        val sectorId = intent.extras?.get(EXTRA_SECTOR) as String

        sectorViewModel = ViewModelProviders.of(this, SectorViewModelFactory(application, sectorId))
            .get(SectorViewModel::class.java)


        sectorViewModel.sector.observe(this, Observer { sector ->
            this.sector = sector
            updateSectorUi()
        })

        initRoutesRecyclerView()

        add_route_button.setOnClickListener { addRoute(sectorId) }
        delete_button.setOnPositiveClickListener { deleteSector() }

    }

    fun updateSectorUi() {
        nameText.text = sector.name
    }

    fun initRoutesRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        routesRecyclerView.setHasFixedSize(true)
        routesRecyclerView.layoutManager = linearLayoutManager
        routesRecyclerView.adapter = RoutesAdapter()
        routesRecyclerView.addItemDecoration(
            DividerItemDecoration(
                routesRecyclerView.context,
                linearLayoutManager.orientation
            )
        )

        // clicking brings you to the routedetail
        routesRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                sectorViewModel.sectorRoutes.value?.get(position)?.let { onRouteClicked(it.route_id) }
            }
        })

        // loading the data
        sectorViewModel.sectorRoutes.observe(this, Observer {
            routesRecyclerView.setRecyclerViewProperties(it)
        })
    }

    private fun deleteSector() {
        sectorViewModel.deleteSector(this.sector)
        finish()
    }

    private fun onRouteClicked(route_id: String) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, route_id)
        startActivity(intent)
    }

    /**
     * Add a new route
     * @param sector_id Which sector the route should be part of
     */
    private fun addRoute(sector_id: String) {
        intent = Intent(this, AddRouteActivity::class.java)
        intent.putExtra(EXTRA_SECTOR_ID, sector_id)
        startActivity(intent)
    }

    class RoutesAdapter : LiveDataAdapter<Route>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveDataViewHolder<Route> {
            val inflater = LayoutInflater.from(parent.context)
            return RouteHolder(inflater.inflate(R.layout.route_list_item, parent, false))
        }

        class RouteHolder(itemView: View) : LiveDataViewHolder<Route>(itemView) {
            override fun bind(item: Route) {
                itemView.routeText.text = item.name
                itemView.gradeText.text = item.grade
                itemView.kindText.text = item.kind
            }
        }
    }

    companion object {
        const val EXTRA_SECTOR = "EXTRA_SECTOR"
    }
}
