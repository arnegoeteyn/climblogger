package com.example.climblogger.ui.stats

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.climblogger.R
import com.example.climblogger.util.RouteKind
import kotlinx.android.synthetic.main.activity_stats.*

class StatsActivity : AppCompatActivity(),
    StatsRouteCountFragment.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)


        val pagerAdapter = StatsRouteCountPagerAdapter(supportFragmentManager)
        pager.adapter = pagerAdapter
    }


    class StatsRouteCountPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {
        override fun getCount(): Int = 2


        override fun getItem(position: Int): Fragment {
            if (position == 0) {
                return StatsRouteCountFragment.newInstance(RouteKind.SPORT)
            } else {
                return StatsRouteCountFragment.newInstance(RouteKind.BOULDER)
            }
        }
    }
}
