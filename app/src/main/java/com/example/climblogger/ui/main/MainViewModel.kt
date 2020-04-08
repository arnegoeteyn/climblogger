package com.example.climblogger.ui.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.climblogger.fragments.RoutesFragment

class MainViewModel(application: Application) : AndroidViewModel(application) {

    var tabFragment: MainActivityTabFragment = RoutesFragment
}