package com.example.climblogger.ui.stats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R

class StatsActivity : AppCompatActivity() {

    private lateinit var statsViewModel: StatsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        statsViewModel = ViewModelProviders.of(this).get(StatsViewModel::class.java)

        statsViewModel.routeAmounts.observe(this, Observer { it ->
            for (amount in it) {
                Log.i("HIOERJJIOEJWORJOIJEW", amount.toString())
            }
        })

    }
}
