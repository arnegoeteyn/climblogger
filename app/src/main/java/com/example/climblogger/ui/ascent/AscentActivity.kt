package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import kotlinx.android.synthetic.main.activity_ascent.*

class AscentActivity : AppCompatActivity() {

    private lateinit var ascentViewModel: AscentViewModel
    private var ascent: Ascent? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ascent)

        // get ascent from intent
        val ascent_id = intent.extras?.get(EXTRA_ASCENT) as Int

        ascentViewModel =
            ViewModelProviders.of(this, AscentViewModelFactory(application, ascent_id))
                .get(AscentViewModel::class.java)

        ascentViewModel.ascent.observe(this, Observer {
            it?.let {
                this.ascent = it
                ascentDate.text = it.date
            }
        })

        delete_button.setOnClickListener { deleteAscent() }
    }

    private fun deleteAscent() {
        // ascent will never be null here, ascent is only null when deleted and by then the activity is closed
        assert(ascent != null)
        ascentViewModel.deleteAscent(this.ascent!!)
        finish()
    }

    companion object {
        public const val EXTRA_ASCENT = "EXTRA_ASCENT"
    }
}
