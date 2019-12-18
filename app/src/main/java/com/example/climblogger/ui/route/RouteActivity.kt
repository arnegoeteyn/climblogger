package com.example.climblogger.ui.route

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.AscentsAdapter
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteWithSector
import com.example.climblogger.ui.ascent.AddAscentActivity
import com.example.climblogger.ui.ascent.AscentActivity
import com.example.climblogger.ui.sector.SectorActivity
import com.example.climblogger.util.*
import kotlinx.android.synthetic.main.activity_route.*

class RouteActivity : AppCompatActivity() {

    private lateinit var routeViewModel: RouteViewModel
    private lateinit var ascentsAdapter: LiveDataAdapter<Ascent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        val routeId = intent.extras?.get(EXTRA_ROUTE) as String
        routeViewModel =
            ViewModelProviders.of(this, RouteViewModelFactory(this.application, routeId))
                .get(RouteViewModel::class.java)

        initAscentsRecyclerView()

        routeViewModel.route.observe(this, Observer { route ->
            route?.let {
                updateRouteUi(it)
                delete_button.setOnPositiveClickListener { deleteRoute(it.route) }

                sectorContainer.setOnClickListener { _ ->
                    goToSector(it.route.sector_id)
                }
            }
        })

        routeViewModel.routeAscents.observe(this, Observer {
            ascentsAdapter.setData(it)
        })

        addAscentButton.setOnClickListener { addAscent(routeId) }
        edit_button.setOnClickListener { editRoute(routeId) }
    }

    private fun goToSector(sectorId: String) {
        val intent = Intent(this, SectorActivity::class.java)
        intent.putExtra(SectorActivity.EXTRA_SECTOR, sectorId)
        startActivity(intent)
    }


    private fun deleteRoute(route: Route) {
        routeViewModel.deleteRoute(route)
        finish()
    }

    private fun editRoute(route_id: String) {
        intent = Intent(this, EditRouteActivity::class.java)
        val bundle = Bundle()
        bundle.putString(EditRouteActivity.EXTRA_ROUTE_ID, route_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun addAscent(route_id: String) {
        intent = Intent(this, AddAscentActivity::class.java)
        intent.putExtra(AddAscentActivity.EXTRA_ROUTE_ID, route_id)
        startActivity(intent)
    }

    private fun onAscentClicked(ascentId: String) {
        val intent = Intent(this, AscentActivity::class.java)
        intent.putExtra(AscentActivity.EXTRA_ASCENT, ascentId)
        startActivity(intent)
    }

    private fun updateRouteUi(route: RouteWithSector) {
        name.text = route.route.name
        grade.text = route.route.grade
        comment.text = route.route.comment
        kind.text = route.route.kind
        sector.text = route.sector_name
    }

    private fun initAscentsRecyclerView() {
        ascentsAdapter = AscentsAdapter()
        ascentsRecyclerView.standardInit(ascentsAdapter)

        // clicking brings you to the routedetail
        ascentsRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                routeViewModel.routeAscents.value?.get(position)
                    ?.let { onAscentClicked(it.ascent_id) }
            }
        })

    }


    companion object {
        const val EXTRA_ROUTE = "EXTRA_ROUTE"
    }
}
