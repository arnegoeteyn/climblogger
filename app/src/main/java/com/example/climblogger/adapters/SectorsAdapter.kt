package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.climblogger.R
import com.example.climblogger.data.Sector
import com.example.climblogger.util.LiveDataAdapter

class SectorsAdapter : LiveDataAdapter<Sector>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveDataViewHolder<Sector> {
        val inflater = LayoutInflater.from(parent.context)
        return SectorHolder(
            inflater.inflate(R.layout.list_item_sector, parent, false)
        )
    }

    class SectorHolder(itemView: View) : LiveDataViewHolder<Sector>(itemView) {
        override fun bind(item: Sector) {
            itemView.findViewById<TextView>(R.id.sector_name).text = item.name
        }
    }
}
