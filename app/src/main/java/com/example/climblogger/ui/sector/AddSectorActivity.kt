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

class AddSectorActivity : AppCompatActivity() {

    private lateinit var addSectorViewModel: AddSectorViewModel

    private lateinit var spinner: ItemSpinner<Area>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sector)

        addSectorViewModel = ViewModelProviders.of(this).get(AddSectorViewModel::class.java)

        this.spinner = findViewById(R.id.areaSpinner)


        // check if an areaId is passed along and selct that area
        intent.extras?.let {
            val areaId = intent.extras?.get(EXTRA_AREA_ID) as String
            addSectorViewModel.getArea(areaId).observe(this, Observer {
                initAreaSpinner(it)
            })
        } ?: initAreaSpinner(null)


        addSectorButton.setOnClickListener { addSector() }

    }

    private fun initAreaSpinner(selectedArea: Area?) {
        addSectorViewModel.allAreas.observe(this, Observer { sectors ->
            this.spinner.setData(sectors)
            selectedArea?.let { selectAreaInSpinner(it) }
        })
    }

    private fun selectAreaInSpinner(area: Area) {
        Log.d("SELECTING", area.toString())
        spinner.selectItemInSpinner(area)
    }

    private fun addSector() {
        val commentText = commentTextInput.editText!!.text.toString()
        val sector = Sector(
            nameTextInput.editText!!.text.toString(),
            (areaSpinner.selectedItem as Area).areaId,
            if (commentText.isEmpty()) null else commentText,
            UUID.randomUUID().toString()
        )
        addSectorViewModel.insertSector(sector)
        finish()
    }

    companion object {
        const val EXTRA_AREA_ID = "EXTRA_AREA_ID" // area ID can be passed to already select the area
    }
}
