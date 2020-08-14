package com.movielist.home.presentation

import android.os.Bundle
import com.movielist.core.presentation.fragment.ViewModelFragment
import com.movielist.home.R
import com.movielist.home.databinding.FragmentHomeBinding
import com.movielist.home.di.DaggerHomeComponent

class HomeFragment : ViewModelFragment<FragmentHomeBinding>(R.layout.fragment_home) {

    override fun injectFragment() {
        DaggerHomeComponent.factory()
            .create(coreComponent)
            .inject(this)
    }

    override fun viewCreated(savedInstanceState: Bundle?) {

    }
}