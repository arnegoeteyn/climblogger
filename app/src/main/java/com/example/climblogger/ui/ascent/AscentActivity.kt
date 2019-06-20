package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R

class AscentActivity : AppCompatActivity() {

    private lateinit var ascentViewModel: AscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ascent)

        // get ascent from intent
        val ascent_id = intent.extras?.get(EXTRA_ASCENT) as Int

        ascentViewModel =
            ViewModelProviders.of(this, AscentViewModelFactory(application, ascent_id))
                .get(AscentViewModel::class.java)
    }

    companion object {
        public const val EXTRA_ASCENT = "EXTRA_ASCENT"
    }
}
