package com.example.climblogger.ui.sector

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Sector
import kotlinx.android.synthetic.main.activity_sector.*

class SectorActivity : AppCompatActivity() {

    private lateinit var sectorViewModel: SectorViewModel

    private lateinit var sector: Sector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sector)

        val sectorId = intent.extras?.get(EXTRA_SECTOR) as String

        sectorViewModel = ViewModelProviders.of(this, SectorViewModelFactory(application, sectorId))
            .get(SectorViewModel::class.java)


        sectorViewModel.sector.observe(this, Observer { sector ->
            this.sector = sector
            updateSectorUi()
        })

    }

    fun updateSectorUi() {
        nameText.text = sector.name
    }


    companion object {
        const val EXTRA_SECTOR = "EXTRA_SECTOR"
    }
}
