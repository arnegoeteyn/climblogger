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

class AscentFormFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Route>
    private lateinit var kindSpinner: ItemSpinner<CharSequence>

    private lateinit var modifyAscentViewModel: ModifyAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        modifyAscentViewModel =
            ViewModelProviders.of(requireActivity()).get(ModifyAscentViewModel::class.java)
        loadAscent()
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<Button>(R.id.dateButton).setOnClickListener { selectDate().toString() }

        setupViewListeners()

        Log.d("DEBUG", "View is create")
        loadForm()
    }

    private fun setupViewListeners() {

        date.afterTextChanged { modifyAscentViewModel.ascentDate = it }
        commentTextEditText.afterTextChanged { modifyAscentViewModel.ascentComment = it }

        kindSpinner.onItemChosen {
            modifyAscentViewModel.ascentKind = kindSpinner.getItemAtPosition(it) as String
        }

        routeSpinner.onItemChosen {
            val route = spinner.getItemAtPosition(it) as Route
            modifyAscentViewModel.ascentRouteId = route.route_id
        }
    }

    private fun loadAscent() {
        modifyAscentViewModel.getAscent()?.observe(this, Observer { ascent ->
            ascent?.let {
                modifyAscentViewModel.updateFromAscent(ascent)
            }
        })
    }

    private fun loadForm() {
        date.text = modifyAscentViewModel.ascentDate
        commentTextEditText.setText(modifyAscentViewModel.ascentComment)
        initRouteSpinner(modifyAscentViewModel.ascentRouteId)
        initKindSpinner(modifyAscentViewModel.ascentKind)
    }

    private fun initRouteSpinner(selectedRouteId: String?) {
        modifyAscentViewModel.allRoutes.observe(
            viewLifecycleOwner, Observer { routes ->
                this.spinner.setData(routes)

                selectedRouteId?.let {
                    modifyAscentViewModel.getRoute(selectedRouteId).observe(this, Observer {
                        spinner.selectItemInSpinner(it)
                    })
                }
            })
    }

    private fun initKindSpinner(selectedKind: String?) {
        kindSpinner.setData(resources.getStringArray(R.array.ascent_kind).toList())
        kindSpinner.selectItemInSpinner(selectedKind)
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


    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener


    companion object {
        @JvmStatic
        fun newInstance() = AscentFormFragment()

        val TAG = this::class.qualifiedName!!
    }
}
