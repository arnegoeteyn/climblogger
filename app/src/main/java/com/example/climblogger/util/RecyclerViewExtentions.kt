package com.example.climblogger.util

import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.data.Route

interface RecyclerViewOnItemClickListener {
    fun onItemClicked(position: Int, view: View)
}

fun RecyclerView.addOnItemClickListener(onClickListener: RecyclerViewOnItemClickListener) {
    this.addOnChildAttachStateChangeListener(object :
        RecyclerView.OnChildAttachStateChangeListener {
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

/**
 * Standard init for most recyclerviews we use
 */
fun <T : RecyclerView.ViewHolder> RecyclerView.standardInit(adapter: RecyclerView.Adapter<T>) {
    val linearLayoutManager = LinearLayoutManager(this.context)
    setHasFixedSize(true)
    layoutManager = linearLayoutManager

    this.adapter = adapter

    addItemDecoration(
        DividerItemDecoration(
            context,
            linearLayoutManager.orientation
        )
    )
}

/**
 * LiveDataAdapter class that can handle updates
 * This can be extended and used as a normal adapter, ony the function setData has to be used
 */
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

