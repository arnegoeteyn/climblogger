package com.example.climblogger.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.util.LiveDataAdapter
import kotlinx.android.synthetic.main.list_item_ascent.view.*

class AscentsAdapter : LiveDataAdapter<Ascent>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LiveDataViewHolder<Ascent> {
        val inflater = LayoutInflater.from(parent.context)
        return SectorHolder(inflater.inflate(R.layout.list_item_ascent, parent, false))
    }

    class SectorHolder(itemView: View) : LiveDataViewHolder<Ascent>(itemView) {
        override fun bind(item: Ascent) {
            itemView.date.text = item.date
        }
    }
}
