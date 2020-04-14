package com.example.climblogger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.climblogger.fragments.RoutesFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_bottom_sheet_menu.*


class MenuListDialogFragment : BottomSheetDialogFragment() {
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_bottom_sheet_menu, container, false)
    }

    interface OnFragmentInteractionListener {
        fun onMenuItemClicked(menu_id: Int): Boolean
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.d("DEBUG", "INIt")
        bottom_sheet_navigation.setNavigationItemSelectedListener { menuItem ->
            Log.d("DEBUG", "HIER GEKLIKT")
            listener?.onMenuItemClicked(menuItem.itemId) ?: run {
                false
            }
        }
        super.onViewCreated(view, savedInstanceState)
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

    companion object {
        val TAG: String = MenuListDialogFragment::class.qualifiedName!!

        fun newInstance(): MenuListDialogFragment = MenuListDialogFragment()

    }
}

