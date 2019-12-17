package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.util.ItemSpinner
import com.example.climblogger.util.getStringDate
import kotlinx.android.synthetic.main.fragment_ascent_form.*
import java.util.*

private const val ARG_PARAM_ASCENT_ID = "ASCENT_ID_param"
private const val ARG_PARAM_ROUTE_ID = "ROUTE_ID_PARAM"

class AscentFormFragment : Fragment() {
    private lateinit var ascentId: String
    private var routeId: String? = null

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Route> // pass generic type here for easier
    private lateinit var kindSpinner: ItemSpinner<CharSequence>
    private lateinit var modifyAscentViewModel: ModifyAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_ASCENT_ID)?.let {
                ascentId = it
            }

            bundle.getString(ARG_PARAM_ROUTE_ID)?.let {
                routeId = it
            }
        }

        modifyAscentViewModel = ViewModelProviders.of(this).get(ModifyAscentViewModel::class.java)
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
        date.text = getStringDate()
        modifyAscentViewModel.getAscent(ascentId).observe(this, Observer { ascent ->
            ascent?.let {
                commentTextInput.editText?.setText(it.comment)
                date.text = it.date
                this.routeId = it.route_id
            }

            initKindSpinner(ascent?.kind)
            initRouteSpinner(this.routeId)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadForm()
    }


    fun createAscent(): Ascent {
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
        modifyAscentViewModel.allRoutes.observe(this, androidx.lifecycle.Observer { routes ->
            this.spinner.setData(routes)
            selectedAreaId?.let {
                modifyAscentViewModel.getRoute(it).observe(this, Observer { route ->
                    this.spinner.selectItemInSpinner(route)
                })
            }
        })
    }

    private fun initKindSpinner(kind: String?) {
        kindSpinner.setData(resources.getStringArray(R.array.route_kind).toList())
        kind?.let {
            kindSpinner.selectItemInSpinner(it)
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


    fun selectDate() {
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


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener


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
