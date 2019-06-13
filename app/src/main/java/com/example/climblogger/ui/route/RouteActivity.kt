package com.example.climblogger.ui.route

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.ui.main.MainActivity.Companion.EXTRA_ROUTE
import kotlinx.android.synthetic.main.activity_route.*
import kotlinx.android.synthetic.main.activity_route.view.*
import kotlinx.android.synthetic.main.ascent_list_item.view.*

class RouteActivity : AppCompatActivity() {

    private lateinit var routeViewModel: RouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_route)

        // get route from intent
        val route_id = intent.extras?.get(EXTRA_ROUTE) as Int

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

        routeViewModel.route.observe(this, Observer { setRouteViews(it) })
        routeViewModel.routeAscents.observe(this, Observer {
            setRecyclerViewProperties(ascentsRecyclerView, it)
        })


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
            return AscentHolder(inflater.inflate(R.layout.ascent_list_item, parent, false))
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

}
