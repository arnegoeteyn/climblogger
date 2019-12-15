package com.example.climblogger.ui.sector

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
import kotlinx.android.synthetic.main.fragment_route_form.*

private const val ARG_PARAM_SECTOR_ID = "sector_id_param"
private const val ARG_PARAM_AREA_ID = "AREA_ID_PARAM"

class SectorFormFragment : Fragment() {
    private var sector_id: String = ""
    private var area_id: String? = ""

    private var listener: OnFragmentInteractionListener? = null


    private lateinit var spinner: ItemSpinner<Area> // pass generic type here for easier

    private lateinit var addSectorViewModel: AddSectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the sector_id
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_SECTOR_ID)?.let {
                sector_id = it
            }

            // get area if it's passed along
            bundle.getString(ARG_PARAM_AREA_ID)?.let {
                area_id = it
            }
        }


        addSectorViewModel = ViewModelProviders.of(this).get(AddSectorViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_sector_form, container, false)
        this.spinner = view.findViewById(R.id.areaSpinner)

        return view
    }

    private fun loadForm() {
        // if route already exists load it
        addSectorViewModel.getRoute(sector_id).observe(this, Observer { sector ->
            sector?.let {
                nameTextInput.editText?.setText(it.name)
                commentTextInput.editText?.setText(it.comment)
                area_id = it.areaId
            }
            // load the spinners and update them with the already selected info
//            initAreaSpinner(sector?.areaId)

            area_id?.let {
                initAreaSpinner(it)
            } ?: run {
                initAreaSpinner(null)
            }
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // if route exists already fill in details
        loadForm()
    }


    public fun createSector(): Sector {
        val commentText = commentTextInput.editText!!.text.toString()

        return Sector(
            nameTextInput.editText!!.text.toString(),
            (spinner.selectedItem as Area).areaId,
            if (commentText.isEmpty()) null else commentText,
            sector_id
        )
    }

    private fun initAreaSpinner(selectedAreaId: String?) {
        addSectorViewModel.allAreas.observe(this, androidx.lifecycle.Observer { areas ->
            this.spinner.setData(areas)
            selectedAreaId?.let { selectAreaInSpinner(it) }
        })
    }

    private fun selectAreaInSpinner(area_id: String) {
        // ugly
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as Area).areaId == area_id) {
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
        fun newInstance(sector_id: String, area_id: String? = null) =
            SectorFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_SECTOR_ID, sector_id)
                    putString(ARG_PARAM_AREA_ID, area_id)
                }
            }
    }
}
