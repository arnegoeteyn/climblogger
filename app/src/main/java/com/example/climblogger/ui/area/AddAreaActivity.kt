package com.example.climblogger.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.ui.sector.ModifySectorViewModelFactory
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var addAreaViewModel: ModifyAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        addAreaViewModel =
            ViewModelProviders.of(this, ModifyAreaViewModelFactory(application, null))
                .get(ModifyAreaViewModel::class.java)


        supportFragmentManager.addIfNotAlreadythere(AreaFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                AreaFormFragment.newInstance(),
                AreaFormFragment.TAG
            )
        }

        confirmationButton.setOnClickListener { addArea() }
        confirmationButton.text = resources.getText(R.string.add_area)
    }


    private fun addArea() {
        addAreaViewModel.insertArea()
        finish()
    }

}
