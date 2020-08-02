package com.an2.core.common.base.di.module

import androidx.lifecycle.ViewModelProvider
import com.an2.core.common.base.presentation.viewmodel.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
abstract class BaseViewModelModule {
    @Provides
    fun provideViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory {
        return viewModelFactory
    }
}