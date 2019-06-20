package com.example.climblogger.ui.ascent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import kotlinx.android.synthetic.main.activity_ascent.*

class AscentActivity : AppCompatActivity() {

    private lateinit var ascentViewModel: AscentViewModel
    private var ascentWithRoute: AscentWithRoute? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ascent)

        // get ascent from intent
        val ascent_id = intent.extras?.get(EXTRA_ASCENT) as Int

        ascentViewModel =
            ViewModelProviders.of(this, AscentViewModelFactory(application, ascent_id))
                .get(AscentViewModel::class.java)

        ascentViewModel.ascentWithRoute.observe(this, Observer {
            it?.let {
                this.ascentWithRoute = it
                ascentDate.text = it.ascent.date
                routeName.text = it.route.name
            }
        })

        delete_button.setOnClickListener { deleteAscent() }
        routeName.setOnClickListener { goToRoute() }
    }

    private fun deleteAscent() {
        // ascent will never be null here, ascent is only null when deleted and by then the activity is closed
        assert(ascentWithRoute != null)
        ascentViewModel.deleteAscent(this.ascentWithRoute!!.ascent)
        finish()
    }

    private fun goToRoute() {
        // ascent will never be null here, ascent is only null when deleted and by then the activity is closed
        assert(ascentWithRoute != null)
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, ascentWithRoute!!.ascent.route_id)
        startActivity(intent)
    }

    companion object {
        public const val EXTRA_ASCENT = "EXTRA_ASCENT"
    }
}
