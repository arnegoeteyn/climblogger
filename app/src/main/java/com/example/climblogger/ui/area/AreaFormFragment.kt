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
import com.example.climblogger.util.afterTextChanged
import kotlinx.android.synthetic.main.fragment_area_form.*
import kotlinx.android.synthetic.main.fragment_route_form.nameTextInput

class AreaFormFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    private lateinit var modifyAreaViewModel: ModifyAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        modifyAreaViewModel = ViewModelProviders.of(requireActivity()).get(ModifyAreaViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_area_form, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewListeners()

        loadArea()
        loadForm()
    }

    private fun loadArea() {
        modifyAreaViewModel.getArea()?.observe(this, Observer { area ->
            area?.let {
                modifyAreaViewModel.updateFromArea(area)
            }
            loadForm()
        })
    }

    private fun setupViewListeners() {
        nameTextInput.editText!!.afterTextChanged { modifyAreaViewModel.areaName = it }
        countryTextInput.editText!!.afterTextChanged { modifyAreaViewModel.areaCountry = it }
    }

    private fun loadForm() {
        nameTextInput.editText?.setText(modifyAreaViewModel.areaName)
        countryTextInput.editText?.setText(modifyAreaViewModel.areaCountry)
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
        fun newInstance() = AreaFormFragment()

        val TAG = this::class.qualifiedName!!
    }
}
