package com.example.climblogger.ui.area

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.ui.sector.AddSectorActivity
import com.example.climblogger.ui.sector.AddSectorActivity.Companion.EXTRA_AREA_ID
import kotlinx.android.synthetic.main.activity_area.*

class AreaActivity : AppCompatActivity() {

    private lateinit var areaViewModel: AreaViewModel

    private lateinit var area: Area

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)

        val areaId = intent.extras?.get(EXTRA_AREA) as String

        areaViewModel = ViewModelProviders.of(this, AreaViewModelFactory(application, areaId))
            .get(AreaViewModel::class.java)


        areaViewModel.area.observe(this, Observer { area ->
            area?.let {
                this.area = it
                updateAreaUi()
            }
        })

        delete_button.setOnPositiveClickListener { deleteArea() }
        add_sector_button.setOnClickListener { addSector(areaId) }
    }

    private fun addSector(area_id: String) {
        intent = Intent(this, AddSectorActivity::class.java)
        intent.putExtra(EXTRA_AREA_ID, area_id)
        startActivity(intent)
    }

    private fun updateAreaUi() {
        nameText.text = area.name
        countryText.text = area.country
    }

    private fun deleteArea() {
        areaViewModel.deleteArea(this.area)
        finish()
    }


    companion object {
        const val EXTRA_AREA = "EXTRA_SECTOR"
    }
}
