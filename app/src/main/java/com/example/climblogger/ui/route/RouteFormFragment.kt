package com.example.climblogger.ui.route

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.fragment_route_form.*

private const val ARG_PARAM_ROUTE_ID = "route_ID_PARAM"

class RouteFormFragment : Fragment() {
    private var route_id: String = ""
    private var listener: OnFragmentInteractionListener? = null


    private lateinit var spinner: ItemSpinner<Sector> // pass generic type here for easier
    private lateinit var kindSpinner: Spinner
    private lateinit var addRouteViewModel: AddRouteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the route_id
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_ROUTE_ID)?.let {
                route_id = it
            }
        }

        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        
        initSectorSpinner(null)
        initKindSpinner()

    }


    public fun createRoute(): Route {
        val commentText = commentTextInput.editText!!.text.toString()
        val linkText = linkTextInput.editText!!.text.toString()

        return Route(
            (sectorSpinner.selectedItem as Sector).sectorId,
            nameTextInput.editText!!.text.toString(),
            gradeTextInput.editText!!.text.toString(),
            kindSpinner.selectedItem as String,
            if (commentText.isEmpty()) null else commentText,
            if (linkText.isEmpty()) null else linkText,
            null, null,
            route_id
        )
    }

    /**
     * Initialisation of the spinner to choose sport/boulder/etc
     */
    private fun initKindSpinner() {
        val arrayAdapter =
            ArrayAdapter.createFromResource(requireContext(), R.array.route_kind, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.kindSpinner.adapter = arrayAdapter
    }


    private fun initSectorSpinner(selectedSector: Sector?) {
        addRouteViewModel.allSectors.observe(this, androidx.lifecycle.Observer { sectors ->
            this.spinner.setData(sectors)
            selectedSector?.let { selectSectorInSpinner(it) }
        })
    }

    private fun selectSectorInSpinner(sector: Sector) {
        spinner.selectItemInSpinner(sector)
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

    companion object {
        @JvmStatic
        fun newInstance(route_id: String) =
            RouteFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_ROUTE_ID, route_id)
                }
            }
    }
}
