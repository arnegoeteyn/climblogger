package com.example.climblogger.ui.sector

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
import com.example.climblogger.data.Area
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import com.example.climblogger.util.afterTextChanged
import kotlinx.android.synthetic.main.fragment_route_form.*
import kotlinx.android.synthetic.main.fragment_route_form.commentTextInput
import kotlinx.android.synthetic.main.fragment_route_form.nameTextInput
import kotlinx.android.synthetic.main.fragment_sector_form.*

class SectorFormFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var spinner: ItemSpinner<Area> // pass generic type here for so we can modify more

    private lateinit var modifySectorViewModel: ModifySectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modifySectorViewModel =
            ViewModelProviders.of(requireActivity()).get(ModifySectorViewModel::class.java)
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

        setupViewListeners()

        loadSector()
        loadForm()
    }

    private fun loadSector() {
        modifySectorViewModel.getSector()?.observe(this, Observer { sector ->
            sector?.let {
                modifySectorViewModel.updateFromSector(sector)
            }
            loadForm()
        })
    }

    private fun loadForm() {
        nameTextInput.editText?.setText(modifySectorViewModel.sectorName)
        commentTextInput.editText?.setText(modifySectorViewModel.sectorComment)

        initAreaSpinner(modifySectorViewModel.areaId)
    }

    private fun setupViewListeners() {
        nameTextInput.editText!!.afterTextChanged { modifySectorViewModel.sectorName = it }
        commentTextInput.editText!!.afterTextChanged { modifySectorViewModel.sectorComment = it }

        areaSpinner.onItemChosen {
            modifySectorViewModel.areaId = (areaSpinner.selectedItem as Area).areaId
        }
    }

    private fun initAreaSpinner(selectedAreaId: String?) {
        modifySectorViewModel.allAreas.observe(this, Observer { areas ->
            this.spinner.setData(areas)

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
        fun newInstance() = SectorFormFragment()

        val TAG = this::class.qualifiedName!!
    }
}
