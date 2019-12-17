package com.example.climblogger.ui.area

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import kotlinx.android.synthetic.main.fragment_area_form.*
import kotlinx.android.synthetic.main.fragment_route_form.nameTextInput

private const val ARG_PARAM_AREA_ID = "area_ID_PARAM"

class AreaFormFragment : Fragment() {
    private var areaId: String = ""
    private var listener: OnFragmentInteractionListener? = null


    private lateinit var modifyAreaViewModel: ModifyAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the areaId
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_AREA_ID)?.let {
                areaId = it
            }
        }

        modifyAreaViewModel = ViewModelProviders.of(this).get(ModifyAreaViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_area_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadForm()
    }

    /**
     * Load the form with data that is already in the area
     * Will only do something if the route is already in the db
     */
    private fun loadForm() {
        modifyAreaViewModel.getArea(areaId).observe(this, Observer { area ->
            area?.let {
                nameTextInput.editText?.setText(it.name)
                countryTextInput.editText?.setText(it.country)
            }
        })
    }

    /**
     * Returns the area currently represented in the form
     */
    fun createArea(): Area {
        return Area(
            countryTextInput.editText!!.text.toString(),
            nameTextInput.editText!!.text.toString(),
            areaId
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

    interface OnFragmentInteractionListener

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
