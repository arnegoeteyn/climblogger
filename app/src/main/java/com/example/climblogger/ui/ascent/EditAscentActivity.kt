package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_edit_ascent.*
import java.util.*

class EditAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var editAscentViewModel: EditAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_ascent)


        // unpack bundle
        var ascent_id: String = UUID.randomUUID().toString()
        intent.extras?.let {
            ascent_id = it.getString(EXTRA_ASCENT_ID, UUID.randomUUID().toString())
        }

        editAscentViewModel = ViewModelProviders.of(this).get(EditAscentViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AscentFormFragment.newInstance(ascent_id))
            .commit()

        editAscentButton.setOnClickListener { editArea() }
    }

    private fun editArea() {
        editAscentViewModel.editAscent(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AscentFormFragment).createAscent()
        )
        finish()
    }

    companion object {
        public const val EXTRA_ASCENT_ID = "ASCENT_ID_EDIT_ROUTE_ACTIVITY"
    }

}
