package com.example.climblogger.ui.main.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climblogger.R
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AscentsFragment() : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var ascentViewModel: AscentsViewModel

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
        ascentViewModel = ViewModelProviders.of(this).get(AscentsViewModel::class.java)

        ascentViewModel.allAscentsWithRoute.observe(this, Observer {
            recyclerView.setRecyclerViewProperties(it)
        })
    }

    private fun initRecyclerView() {
        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter =
            AscentsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                ascentViewModel.allAscentsWithRoute.value?.get(position)
                    ?.let { listener?.onAscentClicked(it.ascent.ascent_id) }
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
        fun onAscentClicked(ascent_id: String)
    }


    class AscentsAdapter : LiveDataAdapter<AscentWithRoute>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AscentHolder {
            val inflater = LayoutInflater.from(parent.context)
            return AscentHolder(
                inflater.inflate(R.layout.list_item_ascent, parent, false)
            )
        }

        class AscentHolder(itemView: View) : LiveDataViewHolder<AscentWithRoute>(itemView) {

            override fun bind(item: AscentWithRoute) {
                itemView.findViewById<TextView>(R.id.date).text = item.ascent.date
                itemView.findViewById<TextView>(R.id.route).text = item.route.toString()
            }
        }
    }

    companion object : MainActivityTabFragment {

        @JvmStatic
        override fun newInstance() = AscentsFragment()

        override val TAG = AscentsFragment::class.qualifiedName!!
    }

}
