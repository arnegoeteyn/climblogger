package com.example.climblogger.ui.main

import androidx.fragment.app.Fragment

interface MainActivityTabFragment {
    val TAG: String
    fun newInstance(): Fragment
}