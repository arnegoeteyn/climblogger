package com.example.climblogger.util

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

/**
 * Extention to wrap calls to fragmentmanagers in commit functions and all that
 */
inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
    val fragmentTransaction = beginTransaction()
    fragmentTransaction.func()
    fragmentTransaction.commit()
}

inline fun FragmentManager.addIfNotAlreadythere(tag: String, func: FragmentTransaction.() -> Unit) {

    val fragment = findFragmentByTag(tag)
    if (fragment == null) {
        inTransaction {
            func()
        }
    }
}