package com.movielist.feature.search.di

import com.movielist.core.common.base.di.CoreComponent
import com.movielist.core.common.base.di.scope.FragmentScope
import dagger.Component

@Component(
        dependencies = [CoreComponent::class]
)
@FragmentScope
interface SearchComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): SearchComponent
    }
}