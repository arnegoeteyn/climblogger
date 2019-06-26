package com.example.climblogger.ui.area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
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
            this.area = area
            updateAreaUi()
        })
    }

    private fun updateAreaUi() {
        nameText.text = area.name
        countryText.text = area.country
    }


    companion object {
        const val EXTRA_AREA = "EXTRA_SECTOR"
    }
}
