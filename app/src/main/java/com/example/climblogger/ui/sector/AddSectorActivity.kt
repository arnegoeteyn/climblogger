package com.example.climblogger.ui.sector

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.data.Sector
import com.example.climblogger.util.setSpinnerData
import kotlinx.android.synthetic.main.activity_add_sector.*
import java.util.*

class AddSectorActivity : AppCompatActivity() {

    private lateinit var addSectorViewModel: AddSectorViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_sector)

        addSectorViewModel = ViewModelProviders.of(this).get(AddSectorViewModel::class.java)

        initAreaSpinner()

        addSectorButton.setOnClickListener { addSector() }
    }

    private fun initAreaSpinner() {
        addSectorViewModel.allAreas.observe(this, Observer { sectors ->
            areaSpinner.setSpinnerData(sectors)
        })
    }

    private fun addSector() {
        val commentText = commentTextInput.editText!!.text.toString()
        val sector = Sector(
            nameTextInput.editText!!.text.toString(),
            (areaSpinner.selectedItem as Area).areaId,
            if (commentText.isEmpty()) null else commentText,
            UUID.randomUUID().toString()
        )
        addSectorViewModel.insertSector(
            sector
        )
        finish()
    }
}
