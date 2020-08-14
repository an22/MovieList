package com.movielist.main.presentation

import android.view.MenuItem
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movielist.core.presentation.activity.BaseActivity
import com.movielist.main.R
import com.movielist.main.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main),
    BottomNavigationView.OnNavigationItemSelectedListener {
    override val navHostId: Int = R.id.nav_host_main

    override fun ActivityMainBinding.setupBinding() {
        bnvMain.setOnNavigationItemSelectedListener(this@MainActivity)
        bnvMain.setOnNavigationItemReselectedListener {}
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_home -> {
                navController.navigate(R.id.fragment_home)
                true
            }
            R.id.nav_search -> {
                navController.navigate(R.id.fragment_search)
                true
            }
            else -> false
        }
    }
}