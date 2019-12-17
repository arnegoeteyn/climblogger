package com.example.climblogger.ui.sector

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
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.fragment_route_form.*

private const val ARG_PARAM_SECTOR_ID = "sector_id_param"
private const val ARG_PARAM_AREA_ID = "AREA_ID_PARAM"

class SectorFormFragment : Fragment() {
    private var sector_id: String = ""
    private var areaId: String? = ""

    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Area> // pass generic type here for so we can modify more

    private lateinit var modifySectorViewModel: ModifySectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get the sector_id
        arguments?.let { bundle ->
            bundle.getString(ARG_PARAM_SECTOR_ID)?.let {
                sector_id = it
            }

            // get area if it's passed along
            bundle.getString(ARG_PARAM_AREA_ID)?.let {
                areaId = it
            }
        }

        modifySectorViewModel = ViewModelProviders.of(this).get(ModifySectorViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_sector_form, container, false)
        this.spinner = view.findViewById(R.id.areaSpinner)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadForm()
    }

    private fun loadForm() {
        // if route already exists load it
        modifySectorViewModel.getSector(sector_id).observe(this, Observer { sector ->
            sector?.let {
                nameTextInput.editText?.setText(it.name)
                commentTextInput.editText?.setText(it.comment)

                // if sector already has areaId
                this.areaId = it.areaId
            }

            // init the areaSpinner
            initAreaSpinner(this.areaId)
        })
    }


    fun createSector(): Sector {
        val commentText = commentTextInput.editText!!.text.toString()

        return Sector(
            nameTextInput.editText!!.text.toString(),
            (spinner.selectedItem as Area).areaId,
            if (commentText.isEmpty()) null else commentText,
            sector_id
        )
    }

    private fun initAreaSpinner(selectedAreaId: String?) {
        modifySectorViewModel.allAreas.observe(this, Observer { areas ->
            this.spinner.setData(areas)

            // if an areaId is passed along we should select it
            selectedAreaId?.let {
                modifySectorViewModel.getArea(it).observe(this, Observer { area: Area? ->
                    spinner.selectItemInSpinner(area)
                })
            }
        })
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
        fun newInstance(sector_id: String, area_id: String? = null) =
            SectorFormFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM_SECTOR_ID, sector_id)
                    putString(ARG_PARAM_AREA_ID, area_id)
                }
            }
    }
}
