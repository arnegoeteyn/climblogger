package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Route
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE_ID
import kotlinx.android.synthetic.main.activity_add_ascent.*
import java.util.*
import javax.xml.datatype.DatatypeConstants.MONTHS

class AddAscentActivity : AppCompatActivity() {

    private lateinit var addAscentViewModel: AddAscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ascent)

        val route_id = intent.extras?.get(EXTRA_ROUTE_ID) as Int

        addAscentViewModel = ViewModelProviders.of(this, AddAscentViewModelFactory(this.application, route_id))
            .get(AddAscentViewModel::class.java)

        addAscentViewModel.route.observe(this, Observer {
            updateRouteUiParts(it)
        })

        dateButton.setOnClickListener { openDatePicker().toString() }

    }

    private fun updateRouteUiParts(route: Route) {
        routeName.text = route.name
    }

    private fun openDatePicker(){
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            date.text = getString(R.string.date_view, dayOfMonth, monthOfYear, year)
        }, year, month, day)

        dpd.show()
    }
}
