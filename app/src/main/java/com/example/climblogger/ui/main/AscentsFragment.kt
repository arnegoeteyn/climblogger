package com.example.climblogger.ui.main

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
import com.example.climblogger.data.Ascent
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AscentsFragment : Fragment() {
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

        ascentViewModel = ViewModelProviders.of(this).get(AscentsViewModel::class.java)

        ascentViewModel.allAscents.observe(this, Observer {
            recyclerView.setRecyclerViewProperties(it)
        })


        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = AscentsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))
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

    interface OnFragmentInteractionListener {}


    class AscentsAdapter : LiveDataAdapter<Ascent>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AscentHolder {
            val inflater = LayoutInflater.from(parent.context)
            return AscentHolder(inflater.inflate(R.layout.ascent_list_item, parent, false))
        }

        class AscentHolder(itemView: View) : LiveDataViewHolder<Ascent>(itemView) {

            override fun bind(item: Ascent) {
                itemView.findViewById<TextView>(R.id.date).text = item.date
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = AscentsFragment()

        val TAG = AscentsFragment::class.qualifiedName
    }
}
