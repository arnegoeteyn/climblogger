package com.example.climblogger.ui.main

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.climblogger.R
import com.example.climblogger.data.Sector
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class SectorsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var sectorsViewModel: SectorsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sectorsViewModel = ViewModelProviders.of(this).get(SectorsViewModel::class.java)
    }

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

        sectorsViewModel.allSectors.observe(this, Observer {
            recyclerView.setRecyclerViewProperties(it)
        })
    }

    private fun initRecyclerView() {
        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = SectorsAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

//        // add an onclicklistener for the recyclerview
//        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
//            override fun onItemClicked(position: Int, view: View) {
//                // some null safety checking
//                ascentViewModel.allAscentsWithRoute.value?.get(position)
//                    ?.let { listener?.onAscentClicked(it.ascent.ascent_id) }
//            }
//        })
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

    class SectorsAdapter : LiveDataAdapter<Sector>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveDataViewHolder<Sector> {
            val inflater = LayoutInflater.from(parent.context)
            return SectorHolder(inflater.inflate(R.layout.list_item_sector, parent, false))
        }

        class SectorHolder(itemView: View) : LiveDataViewHolder<Sector>(itemView) {
            override fun bind(item: Sector) {
                itemView.findViewById<TextView>(R.id.sector_name).text = item.name
            }
        }
    }

    companion object : MainActivityTabFragment {
        override val TAG: String = SectorsFragment::class.qualifiedName!!

        @JvmStatic
        override fun newInstance() = SectorsFragment()
    }
}
