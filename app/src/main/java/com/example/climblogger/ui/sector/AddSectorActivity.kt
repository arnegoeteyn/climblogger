package com.example.climblogger.ui.sector

import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.activity_add_sector.*
import java.util.*

class AddSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var addSectorViewModel: AddSectorViewModel

    private lateinit var spinner: ItemSpinner<Area>

    private var area_id: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sector)

        intent.extras?.let {
            // a sector_id has been passed along
            area_id = intent.extras?.get(EXTRA_AREA_ID) as String
        }

        addSectorViewModel = ViewModelProviders.of(this).get(AddSectorViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentPlace,
                SectorFormFragment.newInstance(UUID.randomUUID().toString(), area_id)
            )
            .commit()


        addSectorButton.setOnClickListener { addSector() }

    }

    private fun addSector() {
        addSectorViewModel.insertSector(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as SectorFormFragment).createSector()
        )
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID =
            "EXTRA_AREA_ID" // area ID can be passed to already select the area
    }
}
