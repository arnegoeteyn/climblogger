package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
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

        initRouteSpinner()
        initKindSpinner()

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.dateButton).setOnClickListener { selectDate().toString() }

        setupViewListeners()

    }

    private fun setupViewListeners() {

        date.afterTextChanged { modifyAscentViewModel.setDate(it) }
        commentTextEditText.afterTextChanged { modifyAscentViewModel.setComment(it) }

        kindSpinner.onItemChosen {
            modifyAscentViewModel.setKind(kindSpinner.getItemAtPosition(it) as String)
        }

        routeSpinner.onItemChosen {
            Log.d(TAG, "chosen $it")
            val route = spinner.getItemAtPosition(it) as Route
            modifyAscentViewModel.setRouteUUID(route.route_id)
        }

        modifyAscentViewModel.getAscent().observe(viewLifecycleOwner, Observer { ascent ->
            ascent?.let {
                loadForm(it)
            }
        })

    }

    private fun loadForm(ascent: Ascent.AscentDraft) {
        date.text = ascent.date
        commentTextInput.editText?.setTextIfNotFocused(ascent.comment)

        selectItemInKindSpinner(ascent.kind)

        ascent.route_id?.let {
            modifyAscentViewModel.getRoute(it)
                .observe(viewLifecycleOwner, Observer { route ->
                    selectItemInRouteSpinner(route)
                })
        }
    }


    fun createAscent() {
        modifyAscentViewModel.insertAscent()
    }

    private fun initRouteSpinner() {
        modifyAscentViewModel.allRoutes.observe(
            viewLifecycleOwner, Observer { routes ->
                this.spinner.setData(routes)
                modifyAscentViewModel.invalidate()
            })
    }

    private fun initKindSpinner() {
        kindSpinner.setData(resources.getStringArray(R.array.ascent_kind).toList())
    }

    private fun selectItemInRouteSpinner(selectedRoute: Route?) {
        Log.d(TAG, "selecting ${selectedRoute?.route_id}")
        this.spinner.selectItemInSpinner(selectedRoute)
    }

    private fun selectItemInKindSpinner(selectedKind: String?) {
        this.kindSpinner.selectItemInSpinner(selectedKind)
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
