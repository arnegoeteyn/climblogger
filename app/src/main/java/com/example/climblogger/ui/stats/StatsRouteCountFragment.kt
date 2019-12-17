package com.example.climblogger.ui.stats

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.RouteAmount
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RouteKind
import kotlinx.android.synthetic.main.fragment_stats_route_count.*
import kotlinx.android.synthetic.main.route_amount_list_item.view.*

private const val ARG_KIND = "kind"

class StatsRouteCountFragment : Fragment() {

    private lateinit var stasRouteCountViewModel: StatsRouteCountViewModel

    private var kind: RouteKind? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            kind = it.getSerializable(ARG_KIND) as RouteKind?
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_stats_route_count, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        stasRouteCountViewModel =
            ViewModelProviders.of(this).get(StatsRouteCountViewModel::class.java)

        initRecyclerView()
    }

    private fun initRecyclerView() {
        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)

        recyclerView.setHasFixedSize(true)

        recyclerView.layoutManager = linearLayoutManager

        val ada = RouteCountAdapter()
        recyclerView.adapter = ada
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                linearLayoutManager.orientation
            )
        )

        var toObserve: LiveData<List<RouteAmount>> = stasRouteCountViewModel.sportAmounts
        when (kind) {
            RouteKind.BOULDER -> toObserve = stasRouteCountViewModel.boulderAmounts
            RouteKind.SPORT -> toObserve = stasRouteCountViewModel.sportAmounts
        }
        // listen for changes in the data
        toObserve.observe(this, Observer {
            ada.setData(it)
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
    }

    class RouteCountAdapter : LiveDataAdapter<RouteAmount>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RouteAmountHolder {
            val inflater = LayoutInflater.from(parent.context)
            return RouteAmountHolder(
                inflater.inflate(
                    R.layout.route_amount_list_item,
                    parent,
                    false
                )
            )
        }

        class RouteAmountHolder(itemView: View) : LiveDataViewHolder<RouteAmount>(itemView) {

            override fun bind(item: RouteAmount) {
                itemView.gradeText.text = item.grade
                itemView.amountText.text = item.amount.toString()
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(kind: RouteKind) =
            StatsRouteCountFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_KIND, kind)
                }
            }
    }
}
