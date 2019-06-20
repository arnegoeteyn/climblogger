package com.example.climblogger.util

import android.content.Context
import android.util.Log
import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.ui.route.RouteActivity

interface RecyclerViewOnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}

fun RecyclerView.addOnItemClickListener(onClickListener: RecyclerViewOnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object : RecyclerView.OnChildAttachStateChangeListener {
        override fun onChildViewDetachedFromWindow(view: View) {
            view.setOnClickListener(null)
        }

        override fun onChildViewAttachedToWindow(view: View) {
            view.setOnClickListener {
                val holder = getChildViewHolder(view)
                onClickListener.onItemClicked(holder.adapterPosition, view)
            }
        }
    })
}

@BindingAdapter("data")
fun <T> RecyclerView.setRecyclerViewProperties(items: List<T>) {
    if (adapter is LiveDataAdapter<*>) {
        (adapter as LiveDataAdapter<T>).setData(items)
    }
}

abstract class LiveDataAdapter<T> : RecyclerView.Adapter<LiveDataAdapter.LiveDataViewHolder<T>>() {
    var items = emptyList<T>()

    fun setData(items: List<T>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: LiveDataViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }

    abstract class LiveDataViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: T)
    }
}

