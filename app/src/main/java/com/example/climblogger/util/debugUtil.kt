package com.example.climblogger.util

import android.util.Log
import androidx.fragment.app.FragmentManager

fun debugFragments(fm: FragmentManager, tag: String = "DEBUG") {
    Log.d(tag, "Found ${fm.backStackEntryCount} fragments:")
    for (entry in 0 until fm.backStackEntryCount) {
        Log.d(
            tag, " Found fragment: " + fm.getBackStackEntryAt(entry).id
        )
    }
}
