package com.example.climblogger.ui.area

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.adapters.SectorsAdapter
import com.example.climblogger.data.Area
import com.example.climblogger.data.Sector
import com.example.climblogger.ui.sector.AddSectorActivity
import com.example.climblogger.ui.sector.SectorActivity
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.standardInit
import kotlinx.android.synthetic.main.activity_area.*
import kotlinx.android.synthetic.main.fragment_main_recyclerview.*

class AreaActivity : AppCompatActivity() {

    private lateinit var areaViewModel: AreaViewModel
    private lateinit var sectorsAdapter: LiveDataAdapter<Sector>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)


        val areaId = intent.extras?.getString(EXTRA_AREA) as String
        areaViewModel = ViewModelProviders.of(this, AreaViewModelFactory(application, areaId))
            .get(AreaViewModel::class.java)

        initSectorsRecyclerView()

        // if an area is passed we can show it
        areaViewModel.area.observe(this, Observer { area ->
            area?.let {
                updateAreaUi(it)
                delete_button.setOnPositiveClickListener { deleteArea(area) }
            }
        })

        // all sectors of that area can be shown in the recyclerview
        areaViewModel.areaSectors.observe(this, Observer {
            sectorsAdapter.setData(it)
        })

        // make the buttons do what they need to do
        edit_button.setOnClickListener { editArea(areaId) }
        add_sector_button.setOnClickListener { addSector(areaId) }
    }

    private fun initSectorsRecyclerView() {
        sectorsAdapter = SectorsAdapter()
        sectorsRecyclerView.standardInit(sectorsAdapter)

        // clicking brings you to the sectorDetail
        sectorsRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                areaViewModel.areaSectors.value?.get(position)?.let { onSectorClicked(it.sectorId) }
            }
        })

    }

    private fun editArea(area_id: String) {
        intent = Intent(this, EditAreaActivity::class.java)
        val bundle = Bundle()
        bundle.putString(EditAreaActivity.EXTRA_AREA_ID, area_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun onSectorClicked(route_id: String) {
        val intent = Intent(this, SectorActivity::class.java)
        intent.putExtra(SectorActivity.EXTRA_SECTOR, route_id)
        startActivity(intent)
    }

    private fun addSector(area_id: String) {
        intent = Intent(this, AddSectorActivity::class.java)
        intent.putExtra(AddSectorActivity.EXTRA_AREA_ID, area_id)
        startActivity(intent)
    }

    private fun updateAreaUi(area: Area) {
        nameText.text = area.name
        countryText.text = area.country
    }

    private fun deleteArea(area: Area) {
        areaViewModel.deleteArea(area)
        finish()
    }

    companion object {
        const val EXTRA_AREA = "EXTRA_AREA"
    }
}
