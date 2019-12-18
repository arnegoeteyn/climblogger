package com.example.climblogger.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var addAreaViewModel: ModifyAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        addAreaViewModel = ViewModelProviders.of(this).get(ModifyAreaViewModel::class.java)

        // attaching the areaFormFragment
        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AreaFormFragment.newInstance(UUID.randomUUID().toString()))
            .commit()

        confirmationButton.setOnClickListener { addArea() }
        confirmationButton.text = resources.getText(R.string.add_area)
    }


    private fun addArea() {
        addAreaViewModel.insertArea(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AreaFormFragment).createArea()
        )
        finish()
    }

}
