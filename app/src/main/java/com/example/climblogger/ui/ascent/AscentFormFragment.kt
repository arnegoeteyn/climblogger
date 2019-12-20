package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
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
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.util.ItemSpinner
import com.example.climblogger.util.afterTextChanged
import com.example.climblogger.util.getStringDate
import com.example.climblogger.util.setTextIfNotFocused
import kotlinx.android.synthetic.main.fragment_ascent_form.*
import java.util.*


private const val ARG_PARAM_ASCENT_ID = "ASCENT_ID_param"
private const val ARG_PARAM_ROUTE_ID = "ROUTE_ID_PARAM"

class AscentFormFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Route>
    private lateinit var kindSpinner: ItemSpinner<CharSequence>

    private lateinit var modifyAscentViewModel: ModifyAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        modifyAscentViewModel =
            ViewModelProviders.of(requireActivity()).get(ModifyAscentViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ascent_form, container, false)
        this.spinner = view.findViewById(R.id.routeSpinner)
        this.kindSpinner = view.findViewById(R.id.kindSpinner)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initRouteSpinner(null)
        initKindSpinner(null)

        dateButton.setOnClickListener { selectDate().toString() }

        setupViewListeners()
    }

    private fun setupViewListeners() {
        modifyAscentViewModel.getAscent().observe(viewLifecycleOwner, Observer { ascent ->
            ascent?.let {
                Log.d("Ascent", "reloading with " + it.route_id)
                loadForm(it)
            }
        })

        date.afterTextChanged { modifyAscentViewModel.setDate(it) }
        commentTextEditText.afterTextChanged { modifyAscentViewModel.setComment(it) }

        routeSpinner.onItemChosen {
            val route = routeSpinner.getItemAtPosition(it) as Route
            Log.d("Ascent", "about to choose route: ${route.route_id} is in spinner at $it")
            modifyAscentViewModel.setRouteUUID(route.route_id)
        }

        kindSpinner.onItemChosen {
            modifyAscentViewModel.setKind(kindSpinner.getItemAtPosition(it) as String)
        }

    }

    private fun loadForm(ascent: Ascent.AscentDraft) {
        date.text = ascent.date
        commentTextInput.editText?.setTextIfNotFocused(ascent.comment)

        initKindSpinner(ascent.kind)
//        initRouteSpinner(ascent.route_id)
        selectItemInRouteSpinner(ascent.route_id)
    }


    fun createAscent() {
        modifyAscentViewModel.insertAscent()
    }

    private fun initRouteSpinner(selectedRouteId: String?) {
        modifyAscentViewModel.allRoutes.observe(
            viewLifecycleOwner, androidx.lifecycle.Observer { routes ->
                this.spinner.setData(routes)
                selectItemInRouteSpinner(selectedRouteId)
            })
    }

    private fun selectItemInRouteSpinner(selectedRouteId: String?) {
        selectedRouteId?.let {
            modifyAscentViewModel.getRoute(it)
                .observe(viewLifecycleOwner, Observer { route ->
                    this.spinner.selectItemInSpinner(route)
                })
        }
    }

    private fun initKindSpinner(kind: String?) {
        kindSpinner.setData(resources.getStringArray(R.array.ascent_kind).toList())
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

        val TAG = this::class.qualifiedName!!
    }
}
