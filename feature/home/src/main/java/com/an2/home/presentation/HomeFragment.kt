package com.an2.home.presentation

import android.os.Bundle
import com.an2.core.common.base.presentation.fragment.ViewModelFragment
import com.an2.home.R
import com.an2.home.databinding.FragmentHomeBinding
import com.an2.home.di.DaggerHomeComponent

class HomeFragment : ViewModelFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun injectFragment() {
        DaggerHomeComponent.factory()
            .create(coreComponent)
            .inject(this)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

    }
}