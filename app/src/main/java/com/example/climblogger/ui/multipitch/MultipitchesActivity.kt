package com.example.climblogger.ui.multipitch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R

class MultipitchesActivity : AppCompatActivity() {

    private lateinit var multipitchesViewModel: MultipitchesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multipitches)

        multipitchesViewModel = ViewModelProviders.of(this).get(MultipitchesViewModel::class.java)
    }
}
