package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.util.LiveDataAdapter
import kotlinx.android.synthetic.main.list_item_route.view.*

class RoutesAdapter : LiveDataAdapter<Route>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RouteHolder(inflater.inflate(R.layout.list_item_route, parent, false))
    }

    class RouteHolder(itemView: View) : LiveDataViewHolder<Route>(itemView) {

        override fun bind(item: Route) {
            itemView.routeText.text = item.name
            itemView.gradeText.text = item.grade
            itemView.kindText.text = item.kind
        }
    }
}

