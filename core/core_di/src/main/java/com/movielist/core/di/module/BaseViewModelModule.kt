package com.movielist.core.di.module

import androidx.lifecycle.ViewModelProvider
import com.movielist.core.BaseViewModelFactory
import dagger.Module
import dagger.Provides

@Module
abstract class BaseViewModelModule {
    @Provides
    fun provideViewModelFactory(viewModelFactory: BaseViewModelFactory): ViewModelProvider.Factory {
        return viewModelFactory
    }
}