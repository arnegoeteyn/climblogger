package com.example.climblogger.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var editAreaViewModel: ModifyAreaViewModel

    private lateinit var areaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        intent.extras?.let { extras ->
            extras.getString(EXTRA_AREA_ID)?.let { areaId ->
                this.areaId = areaId
            } ?: run { finish(); return }
        } ?: run { finish(); return }


        editAreaViewModel =
            ViewModelProviders.of(this, ModifyAreaViewModelFactory(application, areaId))
                .get(ModifyAreaViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(AreaFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                AreaFormFragment.newInstance(),
                AreaFormFragment.TAG
            )
        }

        confirmationButton.setOnClickListener { editArea() }
        confirmationButton.text = resources.getText(R.string.edit_area)
    }

    private fun editArea() {
        editAreaViewModel.editArea()
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID = "AREA_ID_EDIT_ROUTE_ACTIVITY"
    }

}
