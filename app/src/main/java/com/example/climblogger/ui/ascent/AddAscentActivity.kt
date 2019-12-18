package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var addAscentViewModel: ModifyAscentViewModel
    private lateinit var routeId: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        intent.extras?.let {
            routeId = intent.extras?.get(EXTRA_ROUTE_ID) as String
        }

        addAscentViewModel =
            ViewModelProviders.of(this).get(ModifyAscentViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentPlace,
                AscentFormFragment.newInstance(UUID.randomUUID().toString(), routeId)
            )
            .commit()


        confirmationButton.setOnClickListener { addAscent() }
        confirmationButton.text = resources.getString(R.string.add_ascent)
    }

    private fun addAscent() {
        addAscentViewModel.insertAscent(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as AscentFormFragment).createAscent()
        )
        finish()
    }

    companion object {
        const val EXTRA_ROUTE_ID =
            "EXTRA_ROUTE_ID" // route ID can be passed to already select the route
    }

}
