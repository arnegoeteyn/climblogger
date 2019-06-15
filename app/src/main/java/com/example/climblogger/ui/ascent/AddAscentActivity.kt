package com.example.climblogger.ui.ascent

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.Route
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE_ID
import kotlinx.android.synthetic.main.activity_add_ascent.*
import java.util.*

class AddAscentActivity : AppCompatActivity() {

    private lateinit var addAscentViewModel: AddAscentViewModel

    private var route_id: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_ascent)

        route_id = intent.extras?.get(EXTRA_ROUTE_ID) as Int

        addAscentViewModel = ViewModelProviders.of(this, AddAscentViewModelFactory(this.application, route_id))
            .get(AddAscentViewModel::class.java)

        addAscentViewModel.route.observe(this, Observer {
            updateRouteUiParts(it)
        })

        dateButton.setOnClickListener { selectDate().toString() }
        date.text = getStringDate()

        initKindSpinner()

        addAscentButton.setOnClickListener { addAscent() }
    }

    private fun addAscent() {
        addAscentViewModel.insertAscent(Ascent( route_id, date.text.toString()))
    }

    /**
     * Initialize the spinner too choose the type of ascent
     */
    private fun initKindSpinner() {
        val arrayAdapter =
            ArrayAdapter.createFromResource(this, R.array.ascent_kind, android.R.layout.simple_spinner_item)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }

    private fun updateRouteUiParts(route: Route) {
        routeName.text = route.name
    }

    private fun selectDate() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // Display Selected date in textbox
            date.text = getString(R.string.date_view, year, monthOfYear, dayOfMonth)
        }, year, month, day)

        dpd.show()
    }

    /**
     * Pass nothing to get todays date
     */
    private fun getStringDate(day: Int = -1, month: Int = -1, year: Int = -1): String {
        var newYear = year
        var newMonth = month
        var newDay = day
        if (day == -1) {
            val c = Calendar.getInstance()
            newYear = c.get(Calendar.YEAR)
            newMonth = c.get(Calendar.MONTH)
            newDay = c.get(Calendar.DAY_OF_MONTH)
        }
        return getString(R.string.date_view, newYear, newMonth, newDay)
    }

}
