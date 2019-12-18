package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class EditSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var editSectorViewModel: ModifySectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        // unpack bundle
        var sectorId: String = UUID.randomUUID().toString()
        intent.extras?.let {
            sectorId = it.getString(EXTRA_SECTOR_ID, UUID.randomUUID().toString())
        }

        editSectorViewModel = ViewModelProviders.of(this).get(ModifySectorViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, SectorFormFragment.newInstance(sectorId))
            .commit()

        confirmationButton.setOnClickListener { editSector() }
        confirmationButton.text = resources.getString(R.string.edit_sector)
    }

    private fun editSector() {
        editSectorViewModel.editSector(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as SectorFormFragment).createSector()
        )
        finish()
    }

    companion object {
        const val EXTRA_SECTOR_ID = "SECTOR_ID_EDIT_SECTOR_ACT"
    }

}
