package com.example.climblogger.ui.sector

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Sector
import com.example.climblogger.ui.route.AddRouteActivity
import com.example.climblogger.ui.route.AddRouteActivity.Companion.EXTRA_SECTOR_ID
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

        add_route_button.setOnClickListener { addRoute(sectorId) }
        delete_button.setOnPositiveClickListener { deleteSector() }

    }

    fun updateSectorUi() {
        nameText.text = sector.name
    }

    private fun deleteSector() {
        sectorViewModel.deleteSector(this.sector)
        finish()
    }

    /**
     * Add a new route
     * @param sector_id Which sector the route should be part of
     */
    private fun addRoute(sector_id: String) {
        intent = Intent(this, AddRouteActivity::class.java)
        intent.putExtra(EXTRA_SECTOR_ID, sector_id)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_SECTOR = "EXTRA_SECTOR"
    }
}
