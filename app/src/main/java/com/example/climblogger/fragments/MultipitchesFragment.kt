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
import com.example.climblogger.adapters.MultipitchesAdapter
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.standardInit
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class MultipitchesFragment : Fragment() {

    private var listener: OnFragmentInteractionListener? = null
    private lateinit var multipitchesViewModel: MultipitchesViewModel

    private lateinit var multipitchesAdapter: MultipitchesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multipitchesViewModel = ViewModelProviders.of(this).get(MultipitchesViewModel::class.java)
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

        multipitchesViewModel.allMultipitches.observe(this, Observer {
            multipitchesAdapter.setData(it)
        })
    }

    private fun initRecyclerView() {
        multipitchesAdapter = MultipitchesAdapter()
        recyclerView.standardInit(multipitchesAdapter)

        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                multipitchesViewModel.allMultipitches.value?.get(position)
                    ?.let { listener?.onMultipitchClicked(it.multipitch_uuid) }
            }
        })
    }

    interface OnFragmentInteractionListener {
        fun onMultipitchClicked(multipitchId: String)
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
        override val TAG: String = MultipitchesFragment::class.qualifiedName!!

        @JvmStatic
        override fun newInstance() = MultipitchesFragment()
    }
}
