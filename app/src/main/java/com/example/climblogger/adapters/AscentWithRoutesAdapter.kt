package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.climblogger.R
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.util.LiveDataAdapter

/**
 * Adapter taking care of presenting an ascent with some extra information about the route
 */
class AscentWithRoutesAdapter : LiveDataAdapter<AscentWithRoute>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AscentWithRoutesHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AscentWithRoutesHolder(
            inflater.inflate(R.layout.list_item_ascent, parent, false)
        )
    }

    class AscentWithRoutesHolder(itemView: View) : LiveDataViewHolder<AscentWithRoute>(itemView) {

        override fun bind(item: AscentWithRoute) {
            itemView.findViewById<TextView>(R.id.date).text = item.ascent.date
            itemView.findViewById<TextView>(R.id.route).text = item.route.toString()
        }
    }
}

