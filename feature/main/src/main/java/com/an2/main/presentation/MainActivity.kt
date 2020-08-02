package com.an2.main.presentation

import android.view.MenuItem
import com.an2.core.common.base.presentation.activity.BaseActivity
import com.an2.main.R
import com.an2.main.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

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