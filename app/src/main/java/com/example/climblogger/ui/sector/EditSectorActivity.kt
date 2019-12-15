package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_edit_sector.*
import java.util.*

class EditSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var editSectorViewModel: EditSectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_sector)


        // unpack bundle
        var sector_id: String = UUID.randomUUID().toString()
        intent.extras?.let {
            sector_id = it.getString(EXTRA_SECTOR_ID, UUID.randomUUID().toString())
        }

        editSectorViewModel = ViewModelProviders.of(this).get(EditSectorViewModel::class.java)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentPlace, SectorFormFragment.newInstance(sector_id))
            .commit()

        editSectorButton.setOnClickListener { editSector() }
    }

    private fun editSector() {
        editSectorViewModel.editSector(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as SectorFormFragment).createSector()
        )
        finish()
    }

    companion object {
        public const val EXTRA_SECTOR_ID = "SECTOR_ID_EDIT_SECTOR_ACT"
    }

}
