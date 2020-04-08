package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.data.Multipitch
import com.example.climblogger.util.LiveDataAdapter

class MultipitchesAdapter : LiveDataAdapter<Multipitch>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipitchHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MultipitchHolder(
            inflater.inflate(R.layout.list_item_area, parent, false)
        )
    }

    class MultipitchHolder(itemView: View) : LiveDataViewHolder<Multipitch>(itemView) {

        override fun bind(item: Multipitch) {
            itemView.findViewById<TextView>(R.id.area_name).text = item.name
        }
    }
}
