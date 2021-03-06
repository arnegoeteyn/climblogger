package com.example.climblogger.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.climblogger.R
import com.example.climblogger.adapters.SectorsAdapter
import com.example.climblogger.data.Sector
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.*
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class SectorsFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var sectorsAdapter: LiveDataAdapter<Sector>
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
            sectorsAdapter.setData(it)
        })
    }

    private fun initRecyclerView() {
        sectorsAdapter = SectorsAdapter()
        recyclerView.standardInit(sectorsAdapter)

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                sectorsViewModel.allSectors.value?.get(position)
                    ?.let { listener?.onSectorClicked(it.sectorId) }
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
        fun onSectorClicked(sector_id: String)
    }


    companion object : MainActivityTabFragment {
        override val TAG: String = SectorsFragment::class.qualifiedName!!

        @JvmStatic
        override fun newInstance() = SectorsFragment()
    }
}
