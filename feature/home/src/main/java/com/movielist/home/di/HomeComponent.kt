package com.movielist.home.di

import com.movielist.core.common.base.di.CoreComponent
import com.movielist.core.common.base.di.scope.FragmentScope
import com.movielist.home.di.module.HomeModule
import com.movielist.home.presentation.HomeFragment
import dagger.Component

@Component(
    dependencies = [CoreComponent::class],
    modules = [HomeModule::class]
)
@FragmentScope
interface HomeComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): HomeComponent
    }

    fun inject(fragment: HomeFragment)
}