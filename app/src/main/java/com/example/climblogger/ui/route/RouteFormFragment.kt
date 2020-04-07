package com.example.climblogger.ui.route

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
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import com.example.climblogger.util.afterTextChanged
import com.example.climblogger.util.setTextIfNotFocused
import kotlinx.android.synthetic.main.activity_ascent.*
import kotlinx.android.synthetic.main.fragment_route_form.*

class RouteFormFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null


    private lateinit var spinner: ItemSpinner<Sector> // pass generic type here for easier
    private lateinit var kindSpinner: ItemSpinner<CharSequence>
    private lateinit var addRouteViewModel: ModifyRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addRouteViewModel =
            ViewModelProviders.of(requireActivity()).get(ModifyRouteViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_route_form, container, false)
        this.spinner = view.findViewById(R.id.sectorSpinner)
        this.kindSpinner = view.findViewById(R.id.kindSpinner)
        return view
    }

    private fun loadForm() {
        nameTextInput.editText?.setText(addRouteViewModel.routeName)
        gradeTextInput.editText?.setText(addRouteViewModel.routeGrade)
        commentTextInput.editText?.setText(addRouteViewModel.routeComment)
        linkTextInput.editText?.setText(addRouteViewModel.routeLink)

        initSectorSpinner(addRouteViewModel.sectorId)
        initKindSpinner(addRouteViewModel.routeKind)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()

        loadRoute()
        loadForm()

    }

    private fun loadRoute() {
        addRouteViewModel.getRoute()?.observe(this, Observer { route ->
            route?.let {
                addRouteViewModel.updateFromRoute(it)
            }
            loadForm()
        })
    }

    private fun setupViewListeners() {
        nameTextInput.editText!!.afterTextChanged { addRouteViewModel.routeName = it }
        gradeTextInput.editText!!.afterTextChanged { addRouteViewModel.routeGrade = it }
        commentTextInput.editText!!.afterTextChanged { addRouteViewModel.routeComment = it }
        linkTextInput.editText!!.afterTextChanged { addRouteViewModel.routeLink = it }

        sectorSpinner.onItemChosen {
            addRouteViewModel.sectorId = (sectorSpinner.selectedItem as Sector).sectorId
        }
        kindSpinner.onItemChosen {
            addRouteViewModel.routeKind = kindSpinner.selectedItem.toString()
        }
    }

    private fun initKindSpinner(kind: String?) {
        kindSpinner.setData(resources.getStringArray(R.array.route_kind).toList())
        kindSpinner.selectItemInSpinner(kind)
    }


    private fun initSectorSpinner(selectedSector_id: String?) {
        addRouteViewModel.allSectors.observe(this, androidx.lifecycle.Observer { sectors ->
            this.spinner.setData(sectors)

            selectedSector_id?.let {
                addRouteViewModel.getSector(it).observe(this, Observer { sector: Sector? ->
                    spinner.selectItemInSpinner(sector)
                })
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

    interface OnFragmentInteractionListener

    companion object {
        @JvmStatic
        fun newInstance() = RouteFormFragment()

        val TAG = this::class.qualifiedName!!
    }
}
