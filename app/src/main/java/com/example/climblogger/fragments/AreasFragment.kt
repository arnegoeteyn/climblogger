package com.example.climblogger.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.AreasAdapter
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.standardInit
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AreasFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var areasViewModel: AreasViewModel

    private lateinit var areasAdapter: AreasAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        areasViewModel = ViewModelProviders.of(this).get(AreasViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main_recyclerview, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        areasViewModel.allAreas.observe(this, Observer {
            areasAdapter.setData(it)
        })
    }

    private fun initRecyclerView() {
        areasAdapter = AreasAdapter()
        recyclerView.standardInit(areasAdapter)

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                areasViewModel.allAreas.value?.get(position)
                    ?.let { listener?.onAreaClicked(it.areaId) }
            }
        })
    }

    interface OnFragmentInteractionListener {
        fun onAreaClicked(areaId: String)
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


    companion object : MainActivityTabFragment {
        override val TAG: String = AreasFragment::class.qualifiedName!!
        override val title_id: Int = R.string.text_menu_areas

        @JvmStatic
        override fun newInstance() = AreasFragment()
    }
}
