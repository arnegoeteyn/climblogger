package com.example.climblogger.ui.area

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_edit_route.*
import java.util.*

class EditAreaActivity : AppCompatActivity(), AreaFormFragment.OnFragmentInteractionListener {

    private lateinit var editAreaViewModel: EditAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_route)


        // unpack bundle
        var area_id: String = UUID.randomUUID().toString()
        intent.extras?.let {
            area_id = it.getString(EXTRA_AREA_ID, UUID.randomUUID().toString())
        }

        editAreaViewModel = ViewModelProviders.of(this).get(EditAreaViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AreaFormFragment.newInstance(area_id))
            .commit()

        editRouteButton.setOnClickListener { editArea() }
    }

    private fun editArea() {
        editAreaViewModel.editArea(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AreaFormFragment).createArea()
        )
        finish()
    }

    companion object {
        public const val EXTRA_AREA_ID = "AREA_ID_EDIT_ROUTE_ACTIVITY"
    }

}
