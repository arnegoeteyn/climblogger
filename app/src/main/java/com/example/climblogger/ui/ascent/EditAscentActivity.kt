package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var editAscentViewModel: ModifyAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        // unpack bundle
        var ascentId: String = UUID.randomUUID().toString()
        intent.extras?.let {
            ascentId = it.getString(EXTRA_ASCENT_ID, UUID.randomUUID().toString())
        }

        editAscentViewModel = ViewModelProviders.of(this).get(ModifyAscentViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, AscentFormFragment.newInstance(ascentId))
            .commit()

        confirmationButton.setOnClickListener { editArea() }
        confirmationButton.text = resources.getString(R.string.edit_ascent)
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
