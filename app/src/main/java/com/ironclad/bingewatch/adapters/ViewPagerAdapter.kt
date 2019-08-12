package com.ironclad.bingewatch.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.ironclad.bingewatch.fragments.CelebrityFragment
import com.ironclad.bingewatch.fragments.MovieFragment
import com.ironclad.bingewatch.fragments.TVSeriesFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, private var totalTabs: Int) :
    FragmentPagerAdapter(fragmentManager) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> MovieFragment()
            1 -> TVSeriesFragment()
            2 -> CelebrityFragment()
            else -> Fragment()
        }
    }

    override fun getCount() = totalTabs
}