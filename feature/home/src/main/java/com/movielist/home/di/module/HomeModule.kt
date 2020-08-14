package com.movielist.home.di.module

import androidx.lifecycle.ViewModel
import com.movielist.core.di.annotation.ViewModelKey
import com.movielist.core.di.module.BaseViewModelModule
import com.movielist.home.presentation.HomeViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class HomeModule : BaseViewModelModule() {

    @Provides
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    fun provideHomeViewModel(): ViewModel {
        return HomeViewModel()
    }
}