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
import com.example.climblogger.data.Area
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AreasFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var areasViewModel: AreasViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        areasViewModel = ViewModelProviders.of(this).get(AreasViewModel::class.java)
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

        areasViewModel.allAreas.observe(this, Observer {
            recyclerView.setRecyclerViewProperties(it)
        })
    }

    private fun initRecyclerView() {
        // Setting the recyclerview
        val linearLayoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter =
            AreasAdapter()
        recyclerView.addItemDecoration(DividerItemDecoration(recyclerView.context, linearLayoutManager.orientation))

        // add an onclicklistener for the recyclerview
        recyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                areasViewModel.allAreas.value?.get(position)
                    ?.let { listener?.onAreaClicked(it.areaId) }
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
        fun onAreaClicked(areaId: String)
    }

    class AreasAdapter : LiveDataAdapter<Area>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AreaHolder {
            val inflater = LayoutInflater.from(parent.context)
            return AreaHolder(
                inflater.inflate(R.layout.list_item_area, parent, false)
            )
        }

        class AreaHolder(itemView: View) : LiveDataViewHolder<Area>(itemView) {

            override fun bind(item: Area) {
                itemView.findViewById<TextView>(R.id.area_name).text = item.name
            }
        }
    }

    companion object : MainActivityTabFragment {
        override val TAG: String = AreasFragment::class.qualifiedName!!

        @JvmStatic
        override fun newInstance() = AreasFragment()
    }
}
