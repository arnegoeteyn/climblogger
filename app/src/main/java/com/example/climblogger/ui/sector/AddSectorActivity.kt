package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var modifySectorViewModel: ModifySectorViewModel

    private var areaId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        modifySectorViewModel = ViewModelProviders.of(this).get(ModifySectorViewModel::class.java)

        // check if areaId has been passed along
        intent.extras?.let {
            areaId = intent.extras?.get(EXTRA_AREA_ID) as String
        }

        supportFragmentManager.beginTransaction()
            .add(
                R.id.fragmentPlace,
                SectorFormFragment.newInstance(UUID.randomUUID().toString(), areaId)
            )
            .commit()

        confirmationButton.setOnClickListener { addSector() }
        confirmationButton.text = resources.getString(R.string.add_sector)
    }

    private fun addSector() {
        modifySectorViewModel.insertSector(
            (supportFragmentManager.findFragmentById(R.id.fragmentPlace) as SectorFormFragment).createSector()
        )
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID =
            "EXTRA_AREA_ID" // area ID can be passed to already select the area
    }
}
