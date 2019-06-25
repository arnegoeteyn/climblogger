package com.example.climblogger.ui.area

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Area
import kotlinx.android.synthetic.main.activity_add_area.*
import java.util.*

class AddAreaActivity : AppCompatActivity() {

    private lateinit var addAreaViewModel: AddAreaViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_area)

        addAreaViewModel = ViewModelProviders.of(this).get(AddAreaViewModel::class.java)

        addAreaButton.setOnClickListener { addArea() }
    }

    private fun addArea() {
        addAreaViewModel.insertArea(
            Area(
                countryInput.text.toString(),
                nameInput.text.toString(),
                UUID.randomUUID().toString()
            )
        )
        finish()
    }
}
