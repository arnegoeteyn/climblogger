package com.example.climblogger.ui.area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import kotlinx.android.synthetic.main.activity_add_area.*
import java.util.*

class AddAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var addAreaViewModel: AddAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_area)

        addAreaViewModel = ViewModelProviders.of(this).get(AddAreaViewModel::class.java)

        // attaching the areaFormFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AreaFormFragment.newInstance(UUID.randomUUID().toString()))
            .commit()

        addAreaButton.setOnClickListener { addArea() }
    }

    private fun addArea() {
        addAreaViewModel.insertArea(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AreaFormFragment).createArea()
        )
        finish()
    }

}
