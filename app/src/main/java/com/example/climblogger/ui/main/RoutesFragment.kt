package com.example.climblogger.ui.main

import android.content.Context
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climblogger.R
import com.example.climblogger.adapters.RouteWithAscentsAdapter
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteWithAscents
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*
import kotlinx.android.synthetic.main.route_list_item.view.*


class RoutesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var routesViewModel: RoutesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // communication with the routesViewModel
        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)

        initRecyclerView()
    }


    private fun initRecyclerView() {
        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = linearLayoutManager

        recyclerView.adapter = RouteWithAscentsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                routesViewModel.allRoutes.value?.get(position)?.let { listener?.onRouteClicked(it.route_id) }
            }
        })

        // listen for changes in the data
        routesViewModel.allRoutes.observe(this, Observer {
            recyclerView.setRecyclerViewProperties(it)
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
        fun onRouteClicked(route_id: String)
    }


//    class RoutesAdapter : LiveDataAdapter<RouteWithAscents>() {
//
//        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteHolder {
//            val inflater = LayoutInflater.from(parent.context)
//            return RouteHolder(inflater.inflate(R.layout.route_list_item, parent, false))
//        }
//
//        class RouteHolder(itemView: View) : LiveDataViewHolder<RouteWithAscents>(itemView) {
//
//            override fun bind(item: RouteWithAscents) {
//                itemView.routeText.text = item.name
//                itemView.gradeText.text = item.grade
//                itemView.kindText.text = item.kind
//                itemView.sendText.text = item.amount.toString()
//            }
//        }
//    }

    companion object: MainActivityTabFragment {
        @JvmStatic
        override fun newInstance() = RoutesFragment()

        override val TAG = RoutesFragment::class.qualifiedName!!
    }
}
