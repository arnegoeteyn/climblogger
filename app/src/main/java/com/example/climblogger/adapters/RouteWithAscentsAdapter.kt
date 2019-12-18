package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climblogger.R
import com.example.climblogger.data.RouteWithAscents
import com.example.climblogger.util.LiveDataAdapter
import kotlinx.android.synthetic.main.list_item_route.view.*

class RouteWithAscentsAdapter : LiveDataAdapter<RouteWithAscents>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RouteHolder(inflater.inflate(R.layout.list_item_route, parent, false))
    }

    class RouteHolder(itemView: View) : LiveDataViewHolder<RouteWithAscents>(itemView) {

        override fun bind(item: RouteWithAscents) {
            itemView.routeText.text = item.name
            itemView.gradeText.text = item.grade
            itemView.kindText.text = item.kind
            itemView.sendText.text = item.amount.toString()
        }
    }
}

