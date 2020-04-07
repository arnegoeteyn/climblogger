package com.example.climblogger.ui.ascent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.addIfNotAlreadythere
import kotlinx.android.synthetic.main.activity_fragment_single_button.*

class EditAscentActivity : AppCompatActivity(), AscentFormFragment.OnFragmentInteractionListener {

    private lateinit var editAscentViewModel: ModifyAscentViewModel
    private lateinit var ascentId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_single_button)

        intent.extras?.let { extras ->
            extras.getString(EXTRA_ASCENT_ID)?.let { ascentId ->
                this.ascentId = ascentId
            } ?: run { finish(); return }
        } ?: run { finish(); return }

        editAscentViewModel =
            ViewModelProviders.of(this, ModifyAscentViewModelFactory(application, null, ascentId))
                .get(ModifyAscentViewModel::class.java)

        supportFragmentManager.addIfNotAlreadythere(AscentFormFragment.TAG) {
            replace(R.id.fragmentPlace, AscentFormFragment.newInstance(), AscentFormFragment.TAG)
        }

        confirmationButton.setOnClickListener { editArea() }
        confirmationButton.text = resources.getString(R.string.edit_ascent)
    }

    private fun editArea() {
        editAscentViewModel.updateAscent()
        finish()
    }

    companion object {
        public const val EXTRA_ASCENT_ID = "ASCENT_ID_EDIT_ROUTE_ACTIVITY"
    }

}
