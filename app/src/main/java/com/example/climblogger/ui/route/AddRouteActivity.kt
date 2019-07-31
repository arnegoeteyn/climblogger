package com.example.climblogger.ui.route

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.data.Sector
import com.example.climblogger.util.ItemSpinner
import kotlinx.android.synthetic.main.activity_add_route.*
import java.util.*

class AddRouteActivity : AppCompatActivity() {

    private lateinit var addRouteViewModel: AddRouteViewModel

    private lateinit var spinner: ItemSpinner<Sector> // pass generic type here for easier

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_route)

        addRouteViewModel = ViewModelProviders.of(this).get(AddRouteViewModel::class.java)

        this.spinner = findViewById(R.id.sectorSpinner)

        initKindSpinner()

        // check if an sector_id is passed along and selct that sector
        intent.extras?.let {
            val sector_id = intent.extras?.get(EXTRA_SECTOR_ID) as String
            addRouteViewModel.getSector(sector_id).observe(this, Observer {
                initSectorSpinner(it)
            })
            // otherwise select default sector
        } ?: initSectorSpinner(null)

        addRouteButton.setOnClickListener { addRoute() }

    }

    private fun addRoute() {
        val commentText = commentTextInput.editText!!.text.toString()
        val linkText = linkTextInput.editText!!.text.toString()
        val route = Route(
            (sectorSpinner.selectedItem as Sector).sectorId,
            nameTextInput.editText!!.text.toString(),
            gradeTextInput.editText!!.text.toString(),
            kindSpinner.selectedItem as String,
            if (commentText.isEmpty()) null else commentText,
            if (linkText.isEmpty()) null else linkText,
            null, null,
            UUID.randomUUID().toString()
        )
        addRouteViewModel.insertRoute(
            route
        )
        finish()
    }

    private fun initSectorSpinner(selectedSector: Sector?) {
        addRouteViewModel.allSectors.observe(this, Observer { sectors ->
            this.spinner.setData(sectors)
            selectedSector?.let { selectSectorInSpinner(it) }
        })
    }

    private fun selectSectorInSpinner(sector: Sector) {
        spinner.selectItemInSpinner(sector)
    }


    private fun initKindSpinner() {
        val arrayAdapter =
            ArrayAdapter.createFromResource(this, R.array.route_kind, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        kindSpinner.adapter = arrayAdapter
    }

    companion object {
        const val EXTRA_SECTOR_ID = "EXTRA_SECTOR_ID" // pass route id to already select route in spinner
    }
}
