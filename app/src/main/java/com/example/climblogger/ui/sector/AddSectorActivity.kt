package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*
import java.util.*

class AddSectorActivity : AppCompatActivity(), SectorFormFragment.OnFragmentInteractionListener {

    private lateinit var modifySectorViewModel: ModifySectorViewModel

    private lateinit var areaId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)


        intent.extras?.let { extras ->
            extras.getString(EXTRA_AREA_ID)?.let { areaId ->
                this.areaId = areaId
            } ?: run { finish(); return }
        } ?: run { finish(); return }

        modifySectorViewModel =
            ViewModelProviders.of(this, ModifySectorViewModelFactory(application, null, areaId))
                .get(ModifySectorViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(SectorFormFragment.TAG) {
            replace(
                R.id.fragmentPlace,
                SectorFormFragment.newInstance(),
                SectorFormFragment.TAG
            )
        }

        confirmationButton.setOnClickListener { addSector() }
        confirmationButton.text = resources.getString(R.string.add_sector)
    }

    private fun addSector() {
        modifySectorViewModel.insertSector()
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID =
            "EXTRA_AREA_ID" // area ID can be passed to already select the area
    }
}
