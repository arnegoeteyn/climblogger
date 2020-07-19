package com.example.climblogger

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import com.example.climblogger.data.Ascent
import com.example.climblogger.fragments.AscentsFragment
import com.example.climblogger.fragments.RoutesFragment
import com.example.climblogger.ui.main.MainActivityTabFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.fragment_bottom_sheet_menu.*


class SettingsDialogFragment : BottomSheetDialogFragment() {
    private var listener: OnFragmentInteractionListener? = null
    var fragmentTag: String? = null

    var menu: Menu? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_bottom_sheet_settings, container, false)
        menu = view.findViewById<NavigationView>(R.id.bottom_sheet_navigation).menu
        fragmentTag?.let { setGroupVisibilty(it) }
        return view
    }

    fun setGroupVisibilty(fragmentTag: String) {
        when (fragmentTag) {
            RoutesFragment.TAG -> enableMenuGroup(R.id.routes_options)
            AscentsFragment.TAG -> enableMenuGroup(R.id.ascents_options)
        }
    }

    private fun enableMenuGroup(groupId: Int) {
        this.menu?.setGroupVisible(groupId, true)
    }

    interface OnFragmentInteractionListener {
        fun onSettingsItemClicked(menu_id: Int): Boolean
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        bottom_sheet_navigation.setNavigationItemSelectedListener { menuItem ->
            listener?.onSettingsItemClicked(menuItem.itemId) ?: run {
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
        val TAG: String = SettingsDialogFragment::class.qualifiedName!!

        fun newInstance(): SettingsDialogFragment = SettingsDialogFragment()

    }
}

