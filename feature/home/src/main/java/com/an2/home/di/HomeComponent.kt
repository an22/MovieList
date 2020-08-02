package com.an2.home.di

import com.an2.core.common.base.di.CoreComponent
import com.an2.core.common.base.di.scope.FragmentScope
import com.an2.home.di.module.HomeModule
import com.an2.home.presentation.HomeFragment
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