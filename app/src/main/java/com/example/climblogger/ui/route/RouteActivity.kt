package com.example.climblogger.ui.route

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.ui.ascent.AddAscentActivity
import com.example.climblogger.ui.ascent.AddAscentActivity.Companion.EXTRA_ROUTE_ID
import com.example.climblogger.ui.ascent.AscentActivity
import com.example.climblogger.ui.ascent.AscentActivity.Companion.EXTRA_ASCENT
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.list_item_ascent.view.*

class RouteActivity : AppCompatActivity() {

    private lateinit var routeViewModel: RouteViewModel

    private lateinit var route: Route

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        // get route from intent
        val route_id = intent.extras?.get(EXTRA_ROUTE) as String

        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        ascentsRecyclerView.setHasFixedSize(true)
        ascentsRecyclerView.layoutManager = linearLayoutManager
        ascentsRecyclerView.adapter = AscentsAdapter()
        ascentsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                ascentsRecyclerView.context,
                linearLayoutManager.orientation
            )
        )

        routeViewModel = ViewModelProviders.of(this, RouteViewModelFactory(this.application, route_id))
            .get(RouteViewModel::class.java)

        routeViewModel.route.observe(this, Observer { route ->
            route?.let {
                setRouteViews(it)
                this.route = it
            }
        })

        initAscentsRecyclerView()

        addAscentButton.setOnClickListener { addAscent(route_id) }
        edit_button.setOnClickListener { editRoute(route_id) }
        delete_button.setOnPositiveClickListener { deleteRoute() }

    }

    private fun deleteRoute() {
        routeViewModel.deleteRoute(this.route)
        finish()
    }

    private fun editRoute(route_id: String) {
        intent = Intent(this, EditRouteActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putString(EditRouteActivity.EXTRA_ROUTE_ID, route_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun addAscent(route_id: String) {
        intent = Intent(this, AddAscentActivity::class.java)
        intent.putExtra(EXTRA_ROUTE_ID, route_id)
        startActivity(intent)
    }

    private fun onAscentClicked(ascentId: String) {
        val intent = Intent(this, AscentActivity::class.java)
        intent.putExtra(EXTRA_ASCENT, ascentId)
        startActivity(intent)
    }

    private fun setRouteViews(route: Route) {
        name.text = route.name
        grade.text = route.grade
        comment.text = route.comment
        kind.text = route.kind
    }

    private fun initAscentsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        ascentsRecyclerView.setHasFixedSize(true)
        ascentsRecyclerView.layoutManager = linearLayoutManager
        ascentsRecyclerView.adapter = AscentsAdapter()
        ascentsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                ascentsRecyclerView.context,
                linearLayoutManager.orientation
            )
        )

        // clicking brings you to the routedetail
        ascentsRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                routeViewModel.routeAscents.value?.get(position)?.let { onAscentClicked(it.ascent_id) }
            }
        })

        // loading the data
        routeViewModel.routeAscents.observe(this, Observer {
            ascentsRecyclerView.setRecyclerViewProperties(it)
        })
    }


    class AscentsAdapter : LiveDataAdapter<Ascent>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveDataViewHolder<Ascent> {
            val inflater = LayoutInflater.from(parent.context)
            return SectorHolder(inflater.inflate(R.layout.list_item_ascent, parent, false))
        }

        class SectorHolder(itemView: View) : LiveDataViewHolder<Ascent>(itemView) {
            override fun bind(item: Ascent) {
                itemView.date.text = item.date
            }
        }
    }


    companion object {
        public const val EXTRA_ROUTE = "EXTRA_ROUTE"
    }
}
