package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*

class EditSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var editSectorViewModel: ModifySectorViewModel

    private lateinit var sectorId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        intent.extras?.let { extras ->
            extras.getString(EXTRA_SECTOR_ID)?.let { sectorId ->
                this.sectorId = sectorId
            } ?: run { finish(); return }
        } ?: run { finish(); return }

        editSectorViewModel =
            ViewModelProviders.of(this, ModifySectorViewModelFactory(application, sectorId, null))
                .get(ModifySectorViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(SectorFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                SectorFormFragment.newInstance(),
                SectorFormFragment.TAG
            )

        }

        confirmationButton.setOnClickListener { editSector() }
        confirmationButton.text = resources.getString(R.string.edit_sector)
    }

    private fun editSector() {
        editSectorViewModel.editSector()
        finish()
    }

    companion object {
        const val EXTRA_SECTOR_ID = "SECTOR_ID_EDIT_SECTOR_ACT"
    }

}
