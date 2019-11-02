package com.example.climblogger.ui.route

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.fragment_route_form.*

private const val ARG_PARAM_ROUTE_ID = "route_ID_PARAM"
private const val ARG_PARAM_SECTOR_ID = "sector_id_param"

class RouteFormFragment : Fragment() {
    private var route_id: String = ""
    private var sector_id: String? = null
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

            bundle.getString(ARG_PARAM_SECTOR_ID)?.let {
                sector_id = it
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

    /**
     * Load the form with data that is already in the route
     * Will only do something if the route is already in the db
     */
    private fun loadForm(){
        // if route already exists load it
        addRouteViewModel.getRoute(route_id).observe(this, Observer {route ->
            route?.let{
                nameTextInput.editText?.setText(it.name)
                gradeTextInput.editText?.setText(it.grade)
                commentTextInput.editText?.setText(it.comment)
                linkTextInput.editText?.setText(it.link)

            }
            // load the spinners and update them with the already selected info
            initSectorSpinner(route?.sector_id)
            initKindSpinner(route?.kind)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if route exists already fill in details
        loadForm()
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
    private fun initKindSpinner(kind: String?) {
        val arrayAdapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.route_kind,
                android.R.layout.simple_spinner_item
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.kindSpinner.adapter = arrayAdapter
        kind?.let{selectKindInSpinner(kind)}
    }


    private fun initSectorSpinner(selectedSector_id: String?) {
        addRouteViewModel.allSectors.observe(this, androidx.lifecycle.Observer { sectors ->
            this.spinner.setData(sectors)
            selectedSector_id?.let { selectSectorInSpinner(it) }
        })
    }

    private fun selectSectorInSpinner(sector_id: String) {
        // ugly
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as Sector).sectorId == sector_id) {
                spinner.setSelection(i)
            }
        }
//        spinner.selectItemInSpinner(sector)
    }

    private fun selectKindInSpinner(kind: String) {
        // ugly
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i) == kind) {
                spinner.setSelection(i)
            }
        }
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
        fun newInstance(route_id: String, sector_id: String? = null) =
            RouteFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_ROUTE_ID, route_id)
                    putString(ARG_PARAM_SECTOR_ID, sector_id)
                }
            }
    }
}
