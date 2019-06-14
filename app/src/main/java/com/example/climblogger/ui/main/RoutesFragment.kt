package com.example.climblogger.ui.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.climblogger.R
import com.example.climblogger.data.Route
import kotlinx.android.synthetic.main.fragment_routes.*
import kotlinx.android.synthetic.main.route_list_item.view.*


class RoutesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_routes, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // communication with the routesViewModel
        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)

        routesViewModel.allRoutes.observe(this, Observer {
            setRecyclerViewProperties(recyclerView, it)
        })

        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = RoutesAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                routesViewModel.allRoutes.value?.get(position)?.let { listener?.onRouteClicked(it.route_id) }
            }
        })
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onRouteClicked(route_id: Int)
    }


    @BindingAdapter("data")
    fun setRecyclerViewProperties(recyclerView: RecyclerView, items: List<Route>) {
        if (recyclerView.adapter is RoutesAdapter) {
            (recyclerView.adapter as RoutesAdapter).setData(items)
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(position: Int, view: View)
    }

    fun RecyclerView.addOnItemClickListener(onClickListener: OnItemClickListener) {
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

    class RoutesAdapter : RecyclerView.Adapter<RoutesAdapter.RouteHolder>() {

        var routes = emptyList<Route>()

        fun setData(items: List<Route>) {
            routes = items
            notifyDataSetChanged()
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
            val inflater = LayoutInflater.from(parent.context)
            return RouteHolder(inflater.inflate(R.layout.route_list_item, parent, false))
        }

        override fun getItemCount(): Int = routes.size

        override fun onBindViewHolder(holder: RouteHolder, position: Int) {
            holder.bind(routes[position])
        }

        class RouteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

            fun bind(route: Route) {
                itemView.routeText.text = route.name
                itemView.gradeText.text = route.grade
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = RoutesFragment()

        public val TAG = RoutesFragment::class.qualifiedName
    }
}
