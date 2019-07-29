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
        routeViewModel.routeAscents.observe(this, Observer {
            setRecyclerViewProperties(ascentsRecyclerView, it)
        })

        addAscentButton.setOnClickListener { addAscent(route_id) }

        delete_button.setOnPositiveClickListener { deleteRoute() }

    }

    private fun deleteRoute() {
        routeViewModel.deleteRoute(this.route)
        finish()
    }

    private fun addAscent(route_id: String) {
        intent = Intent(this, AddAscentActivity::class.java)
        intent.putExtra(EXTRA_ROUTE_ID, route_id)
        startActivity(intent)
    }

    private fun setRouteViews(route: Route) {
        name.text = route.name
        grade.text = route.grade
        comment.text = route.comment
        kind.text = route.kind
    }

    @BindingAdapter("data")
    fun setRecyclerViewProperties(recyclerView: RecyclerView, items: List<Ascent>) {
        if (recyclerView.adapter is AscentsAdapter) {
            (recyclerView.adapter as AscentsAdapter).setData(items)
        }
    }

    class AscentsAdapter : RecyclerView.Adapter<AscentsAdapter.AscentHolder>() {
        var ascents = emptyList<Ascent>()

        fun setData(items: List<Ascent>) {
            ascents = items
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AscentsAdapter.AscentHolder {
            val inflater = LayoutInflater.from(parent.context)
            return AscentHolder(inflater.inflate(R.layout.list_item_ascent, parent, false))
        }

        override fun getItemCount(): Int = ascents.size

        override fun onBindViewHolder(holder: AscentsAdapter.AscentHolder, position: Int) {
            holder.bind(ascents[position])
        }

        class AscentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(ascent: Ascent) {
                itemView.date.text = ascent.date
            }
        }
    }

    companion object {
        public const val EXTRA_ROUTE = "EXTRA_ROUTE"
    }
}
