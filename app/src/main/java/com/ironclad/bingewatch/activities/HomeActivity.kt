package com.ironclad.bingewatch.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ironclad.bingewatch.R
import com.ironclad.bingewatch.adapters.ViewPageAdapter
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    var tabLayout: TabLayout? = null
    var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Movies"

        tabLayout = findViewById(R.id.tabLayout)
        viewPager = findViewById(R.id.vpMain)

        tabLayout!!.addTab(tabLayout!!.newTab().setText("Movies"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("TV Series"))
        tabLayout!!.addTab(tabLayout!!.newTab().setText("Celebrities"))
        tabLayout!!.tabGravity = TabLayout.GRAVITY_FILL

        val adapter = ViewPageAdapter(supportFragmentManager, tabLayout!!.tabCount)
        viewPager!!.adapter = adapter

        viewPager!!.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabLayout))

        tabLayout!!.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
                toolbar.title = tab.text
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                viewPager!!.currentItem = tab!!.position
                toolbar.title = tab.text
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.account -> {
            true
        }
        else -> super.onOptionsItemSelected(item)
    }
}
