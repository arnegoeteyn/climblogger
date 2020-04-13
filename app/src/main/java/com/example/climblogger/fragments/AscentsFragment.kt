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
import com.example.climblogger.adapters.AscentWithRoutesAdapter
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.*
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AscentsFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var ascentsAdapter: AscentWithRoutesAdapter
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

        ascentViewModel.allAscents.observe(this, Observer {
            it?.let { it1 -> ascentsAdapter.setData(it1) }
        })
    }

    private fun initRecyclerView() {
        ascentsAdapter = AscentWithRoutesAdapter()
        recyclerView.standardInit(ascentsAdapter)

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                ascentViewModel.allAscents.value?.get(position)
                    ?.let { it.ascent?.ascent_id?.let { it1 -> listener?.onAscentClicked(it1) } }
            }
        })

    }

    interface OnFragmentInteractionListener {
        fun onAscentClicked(ascent_id: String)
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

        @JvmStatic
        override fun newInstance() = AscentsFragment()

        override val TAG = AscentsFragment::class.qualifiedName!!
    }

}
