package com.example.climblogger.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.RouteWithAscentsAdapter
import com.example.climblogger.data.RouteWithAscents
import com.example.climblogger.enums.RouteKind
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.*
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*


class RoutesFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var routesAdapter: RouteWithAscentsAdapter
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
        initRecyclerView()
        routesViewModel = ViewModelProviders.of(this).get(RoutesViewModel::class.java)


        observeRouteData(RouteKind.ALL)
    }

    public fun observeRouteData(routeKind: RouteKind) {
        routesViewModel.allRoutes.removeObservers(viewLifecycleOwner)

        var kindFilter: (List<RouteWithAscents>) -> List<RouteWithAscents> = { it }

        if (routeKind != RouteKind.ALL)
            kindFilter = { it.filter { it.route.kind == routeKind.kind } }

        routesViewModel.allRoutes.observe(viewLifecycleOwner, Observer {
            routesAdapter.setData(kindFilter(it))
        })
        Log.d(TAG, "Now observing $routeKind.kind")
    }


    private fun initRecyclerView() {
        routesAdapter = RouteWithAscentsAdapter()
        recyclerView.standardInit(routesAdapter)

        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                routesViewModel.allRoutes.value?.get(position)
                    ?.let { listener?.onRouteClicked(it.route.route_id) }
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
        fun onRouteClicked(route_id: String)
    }

    companion object : MainActivityTabFragment {
        @JvmStatic
        override fun newInstance() = RoutesFragment()

        override val TAG = RoutesFragment::class.qualifiedName!!
        override val title_id: Int = R.string.text_menu_routes
    }
}
