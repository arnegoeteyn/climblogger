package com.example.climblogger.ui.ascent

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.data.Ascent
import com.example.climblogger.data.AscentWithRoute
import com.example.climblogger.ui.route.RouteActivity
import com.example.climblogger.ui.route.RouteActivity.Companion.EXTRA_ROUTE
import com.example.climblogger.util.debugFragments
import kotlinx.android.synthetic.main.activity_ascent.*

class AscentActivity : AppCompatActivity() {

    private lateinit var ascentViewModel: AscentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ascent)

        // get ascent from intent
        val ascentId = intent.extras?.get(EXTRA_ASCENT) as String
        ascentViewModel =
            ViewModelProviders.of(this, AscentViewModelFactory(application, ascentId))
                .get(AscentViewModel::class.java)

        ascentViewModel.ascentWithRoute.observe(this, Observer { ascentWithRoute ->
            ascentWithRoute?.let {
                updateAscentUi(it)
                delete_button.setOnPositiveClickListener { it.ascent?.let { it1 -> deleteAscent(it1) } }

                routeContainer.setOnClickListener { _ ->
                    it.ascent?.route_id?.let { it1 -> goToRoute(it1) }
                }
            }
        })

        editAscentButton.setOnClickListener { editAscent(ascentId) }
    }

    private fun updateAscentUi(ascentWithRoute: AscentWithRoute?) {
        ascentWithRoute?.let {
            ascentDate.text = it.ascent?.date
            routeName.text = it.route?.name
            kind.text = it.ascent?.kind
            comment.text = it.ascent?.comment
        }
    }

    private fun editAscent(ascent_id: String) {
        intent = Intent(this, EditAscentActivity::class.java)
        val bundle = Bundle()
        bundle.putString(EditAscentActivity.EXTRA_ASCENT_ID, ascent_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun deleteAscent(ascent: Ascent) {
        ascentViewModel.deleteAscent(ascent)
        finish()
    }

    private fun goToRoute(routeId: String) {
        val intent = Intent(this, RouteActivity::class.java)
        intent.putExtra(EXTRA_ROUTE, routeId)
        startActivity(intent)
    }

    companion object {
        const val EXTRA_ASCENT = "EXTRA_ASCENT"
        public val TAG = this::class.qualifiedName!!
    }
}
