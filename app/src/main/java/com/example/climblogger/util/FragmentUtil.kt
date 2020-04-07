package com.example.climblogger.util

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.climblogger.ui.main.MainActivityTabFragment

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

fun AppCompatActivity.detachSwitch(
    fragmentId: Int,
    detachTag: String,
    attachFragment: MainActivityTabFragment
) {

    val oldFragment = supportFragmentManager.findFragmentByTag(detachTag)
    val newFragment = supportFragmentManager.findFragmentByTag(attachFragment.TAG)

    oldFragment?.let {
        supportFragmentManager.inTransaction() {
            detach(oldFragment)
        }
    }

    supportFragmentManager.inTransaction {
        newFragment?.let {
            attach(it)
        } ?: run {
            add(fragmentId, attachFragment.newInstance(), attachFragment.TAG)
        }
    }

}

