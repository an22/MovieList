package com.an2.home.di.module

import androidx.lifecycle.ViewModel
import com.an2.core.common.base.di.annotation.ViewModelKey
import com.an2.core.common.base.di.module.BaseViewModelModule
import com.an2.home.presentation.HomeViewModel
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