package com.example.climblogger.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var editAreaViewModel: ModifyAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        // unpack bundle
        var areaId: String = UUID.randomUUID().toString()
        intent.extras?.let {
            areaId = it.getString(EXTRA_AREA_ID, UUID.randomUUID().toString())
        }

        editAreaViewModel = ViewModelProviders.of(this).get(ModifyAreaViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AreaFormFragment.newInstance(areaId))
            .commit()

        confirmationButton.setOnClickListener { editArea() }
        confirmationButton.text = resources.getText(R.string.edit_area)
    }

    private fun editArea() {
        editAreaViewModel.editArea(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AreaFormFragment).createArea()
        )
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID = "AREA_ID_EDIT_ROUTE_ACTIVITY"
    }

}
