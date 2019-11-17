package com.example.climblogger.ui.area

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climblogger.R
import com.example.climblogger.data.Area
import com.example.climblogger.data.Sector
import com.example.climblogger.ui.sector.AddSectorActivity
import com.example.climblogger.ui.sector.AddSectorActivity.Companion.EXTRA_AREA_ID
import com.example.climblogger.ui.sector.SectorActivity
import com.example.climblogger.ui.sector.SectorActivity.Companion.EXTRA_SECTOR
import com.example.climblogger.util.LiveDataAdapter
import com.example.climblogger.util.RecyclerViewOnItemClickListener
import com.example.climblogger.util.addOnItemClickListener
import com.example.climblogger.util.setRecyclerViewProperties
import kotlinx.android.synthetic.main.activity_area.*
import kotlinx.android.synthetic.main.list_item_sector.view.*

class AreaActivity : AppCompatActivity() {

    private lateinit var areaViewModel: AreaViewModel

    private lateinit var area: Area

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_area)

        val areaId = intent.extras?.get(EXTRA_AREA) as String

        areaViewModel = ViewModelProviders.of(this, AreaViewModelFactory(application, areaId))
            .get(AreaViewModel::class.java)


        areaViewModel.area.observe(this, Observer { area ->
            area?.let {
                this.area = it
                updateAreaUi()
            }
        })

        initSectorsRecyclerView()

        delete_button.setOnPositiveClickListener { deleteArea() }
        edit_button.setOnClickListener { editArea(areaId) }
        add_sector_button.setOnClickListener { addSector(areaId) }
    }


    private fun editArea(area_id: String) {
        intent = Intent(this, EditAreaActivity::class.java)
        val bundle: Bundle = Bundle()
        bundle.putString(EditAreaActivity.EXTRA_AREA_ID, area_id)
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun onSectorClicked(route_id: String) {
        val intent = Intent(this, SectorActivity::class.java)
        intent.putExtra(EXTRA_SECTOR, route_id)
        startActivity(intent)
    }

    private fun initSectorsRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        sectorsRecyclerView.setHasFixedSize(true)
        sectorsRecyclerView.layoutManager = linearLayoutManager
        sectorsRecyclerView.adapter = SectorsAdapter()
        sectorsRecyclerView.addItemDecoration(
            DividerItemDecoration(
                sectorsRecyclerView.context,
                linearLayoutManager.orientation
            )
        )

        // clicking brings you to the routedetail
        sectorsRecyclerView.addOnItemClickListener(object : RecyclerViewOnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                // some null safety checking
                areaViewModel.areaSectors.value?.get(position)?.let { onSectorClicked(it.sectorId) }
            }
        })

        // loading the data
        areaViewModel.areaSectors.observe(this, Observer {
            sectorsRecyclerView.setRecyclerViewProperties(it)
        })
    }

    private fun addSector(area_id: String) {
        intent = Intent(this, AddSectorActivity::class.java)
        intent.putExtra(EXTRA_AREA_ID, area_id)
        startActivity(intent)
    }

    private fun updateAreaUi() {
        nameText.text = area.name
        countryText.text = area.country
    }

    private fun deleteArea() {
        areaViewModel.deleteArea(this.area)
        finish()
    }

    class SectorsAdapter : LiveDataAdapter<Sector>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveDataViewHolder<Sector> {
            val inflater = LayoutInflater.from(parent.context)
            return SectorHolder(inflater.inflate(R.layout.list_item_sector, parent, false))
        }

        class SectorHolder(itemView: View) : LiveDataViewHolder<Sector>(itemView) {
            override fun bind(item: Sector) {
                itemView.sector_name.text = item.name
            }
        }
    }

    companion object {
        const val EXTRA_AREA = "EXTRA_SECTOR"
    }
}
