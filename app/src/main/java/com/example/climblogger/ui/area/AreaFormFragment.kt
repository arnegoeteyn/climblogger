package com.example.climblogger.ui.area

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
import com.example.climblogger.data.Area
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.fragment_area_form.*
import kotlinx.android.synthetic.main.fragment_route_form.*
import kotlinx.android.synthetic.main.fragment_route_form.nameTextInput

private const val ARG_PARAM_AREA_ID = "area_ID_PARAM"

class AreaFormFragment : Fragment() {
    private var area_id: String = ""
    private var listener: OnFragmentInteractionListener? = null


    private lateinit var addAreaViewModel: AddAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        // get the route_id
//        arguments?.let { bundle ->
//            bundle.getString(ARG_PARAM_ROUTE_ID)?.let {
//                route_id = it
//            }
//
//            bundle.getString(ARG_PARAM_SECTOR_ID)?.let {
//                sector_id = it
//            }
//        }
//
//
//        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_area_form, container, false)
        return view
    }

    /**
     * Load the form with data that is already in the area
     * Will only do something if the route is already in the db
     */
    private fun loadForm() {
        // if route already exists load it
//        addRouteViewModel.getRoute(route_id).observe(this, Observer {route ->
//            route?.let{
//                nameTextInput.editText?.setText(it.name)
//                gradeTextInput.editText?.setText(it.grade)
//                commentTextInput.editText?.setText(it.comment)
//                linkTextInput.editText?.setText(it.link)
//
//            }
//            // load the spinners and update them with the already selected info
//            initSectorSpinner(route?.sector_id)
//            initKindSpinner(route?.kind)
//        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if route exists already fill in details
        loadForm()
    }


    fun createArea(): Area {
        return Area(
            countryTextInput.editText!!.text.toString(),
            nameTextInput.editText!!.text.toString(),
            area_id
        )
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
        fun newInstance(area_id: String) =
            AreaFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_AREA_ID, area_id)
                }
            }
    }
}
