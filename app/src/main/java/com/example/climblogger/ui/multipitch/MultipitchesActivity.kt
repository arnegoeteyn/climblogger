package com.example.climblogger.ui.multipitch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.climblogger.R
import com.example.climblogger.ui.main.fragments.MultipitchesFragment
import com.example.climblogger.util.inTransaction

class MultipitchesActivity : AppCompatActivity(),
    MultipitchesFragment.OnFragmentInteractionListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipitches)


        supportFragmentManager.inTransaction {
            add(R.id.fragmentPlace, MultipitchesFragment.newInstance(), MultipitchesFragment.TAG)
        }

    }

    override fun onMultipitchClicked(multipitchId: String) {
    }
}
