package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.fragment_ascent_form.*
import java.util.*

private const val ARG_PARAM_ASCENT_ID = "ASCENT_ID_param"
private const val ARG_PARAM_ROUTE_ID = "ROUTE_ID_PARAM"

class AscentFormFragment : Fragment() {
    private var ascent_id: String = ""
    private var route_id: String? = ""

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Route> // pass generic type here for easier
    private lateinit var kindSpinner: Spinner

    private lateinit var addAscentViewModel: AddAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var route_id = ""
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_ASCENT_ID)?.let {
                ascent_id = it
            }

            bundle.getString(ARG_PARAM_ROUTE_ID)?.let {
                this.route_id = it
                route_id = it
            }
        }

        addAscentViewModel = ViewModelProviders.of(
            this,
            AddAscentViewModelFactory(activity!!.application, route_id)
        )
            .get(AddAscentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ascent_form, container, false)
        this.spinner = view.findViewById(R.id.routeSpinner)
        this.kindSpinner = view.findViewById(R.id.kindSpinner)

        val dateButton: Button = view.findViewById(R.id.dateButton)
        dateButton.setOnClickListener { selectDate().toString() }

        return view
    }

    private fun loadForm() {
        addAscentViewModel.getAscent(ascent_id).observe(this, Observer { ascent ->
            ascent?.let {
                commentTextInput.editText?.setText(it.comment)
            }

            initKindSpinner(ascent?.kind)


            route_id?.let {
                initRouteSpinner(it)
            } ?: run {
                initRouteSpinner(null)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if route exists already fill in details
        loadForm()
    }


    public fun createAscent(): Ascent {
        val commentText = commentTextInput.editText!!.text.toString()

        return Ascent(
            (spinner.selectedItem as Route).route_id,
            date.text.toString(),
            spinner.selectedItem.toString(),
            if (commentText.isEmpty()) null else commentText,
            UUID.randomUUID().toString()
        )
    }

    private fun initRouteSpinner(selectedAreaId: String?) {
        addAscentViewModel.allRoutes.observe(this, androidx.lifecycle.Observer { routes ->
            this.spinner.setData(routes)
            selectedAreaId?.let { selectRouteInSpinner(it) }
        })
    }

    private fun selectRouteInSpinner(route_id: String) {
        // ugly
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as Route).route_id == route_id) {
                spinner.setSelection(i)
            }
        }
    }

    private fun initKindSpinner(kind: String?) {
        val arrayAdapter =
            ArrayAdapter.createFromResource(
                requireContext(),
                R.array.ascent_kind,
                android.R.layout.simple_spinner_item
            )
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        this.kindSpinner.adapter = arrayAdapter
        kind?.let { selectKindInSpinner(it) }
    }

    private fun selectKindInSpinner(kind: String) {
        // ugly
        for (i in 0 until kindSpinner.count) {
            if (kindSpinner.getItemAtPosition(i) == kind) {
                kindSpinner.setSelection(i)
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


    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(
            context!!,
            DatePickerDialog.OnDateSetListener { view, myear, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                date.text = getStringDate(dayOfMonth, monthOfYear, myear)
            },
            year,
            month,
            day
        )

        dpd.show()
    }

    /**
     * Pass nothing to get todays date
     */
    private fun getStringDate(day: Int = -1, month: Int = -1, year: Int = -1): String {
        var newYear = year
        var newMonth = month + 1
        var newDay = day
        if (day == -1) {
            val c = Calendar.getInstance()
            newYear = c.get(Calendar.YEAR)
            newMonth = c.get(Calendar.MONTH) + 1
            newDay = c.get(Calendar.DAY_OF_MONTH)
        }
        return getString(R.string.date_view, newYear, newMonth, newDay)
    }


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
    }

    companion object {
        @JvmStatic
        fun newInstance(ascent_id: String, route_id: String? = null) =
            AscentFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_ROUTE_ID, route_id)
                    putString(ARG_PARAM_ASCENT_ID, ascent_id)
                }
            }
    }
}
