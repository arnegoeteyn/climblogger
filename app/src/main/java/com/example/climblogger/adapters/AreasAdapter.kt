package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.util.LiveDataAdapter

class AreasAdapter : LiveDataAdapter<Area>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaHolder {
        val inflater = LayoutInflater.from(parent.context)
        return AreaHolder(
            inflater.inflate(R.layout.list_item_area, parent, false)
        )
    }

    class AreaHolder(itemView: View) : LiveDataViewHolder<Area>(itemView) {

        override fun bind(item: Area) {
            itemView.findViewById<TextView>(R.id.area_name).text = item.name
        }
    }
}
