package com.example.climblogger.ui.multipitch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.RouteWithAscentsAdapter
import com.example.climblogger.data.Multipitch
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.standardInit
import kotlinx.android.synthetic.main.activity_multipitch.*

class MultipitchActivity : AppCompatActivity() {

    private lateinit var multipitchViewModel: MultipitchViewModel
    private lateinit var routesAdapter: RouteWithAscentsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipitch)


        val multipitchId = intent.extras?.getString(EXTRA_MULTIPITCH) as String
        multipitchViewModel =
            ViewModelProviders.of(this, MultipitchViewModelFactory(application, multipitchId))
                .get(MultipitchViewModel::class.java)

        initRoutesRecyclerView()

        multipitchViewModel.multipitch.observe(this, Observer { multipitch ->
            multipitch?.let {
                updateMultipitchUi(it.multipitch)
                Log.d("DEBUG", "HALLO ${it.routes.size}")
                Log.d("DEBUG", "HALLO ${it}")
//                delete_button.setOnPositiveClickListener { }
            }
        })

        multipitchViewModel.multipitchRoutes.observe(this, Observer {
            routesAdapter.setData(it)
        })

//        // make the buttons do what they need to do
//        edit_button.setOnClickListener { editArea(areaId) }
//        add_sector_button.setOnClickListener { addSector(areaId) }
    }

    private fun updateMultipitchUi(multipitch: Multipitch) {
        nameText.text = multipitch.name
    }

    private fun initRoutesRecyclerView() {
        routesAdapter = RouteWithAscentsAdapter()
        routesRecyclerView.standardInit(routesAdapter)

        routesRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                multipitchViewModel.multipitchRoutes.value?.get(position)
                    ?.let { onRouteClicked(it.route_id) }
            }
        })
    }

    private fun onRouteClicked(route_id: String) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(RouteActivity.EXTRA_ROUTE, route_id)
        startActivity(intent)
    }


    companion object {
        const val EXTRA_MULTIPITCH = "EXTRA_MULTIPITCH"
    }
}
