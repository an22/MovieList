package com.an2.login.di

import com.an2.core.common.base.di.CoreComponent
import com.an2.core.common.base.di.scope.ActivityScope
import dagger.Component

@Component(
        dependencies = [CoreComponent::class]
)
@ActivityScope
interface LoginComponent {
    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreComponent): LoginComponent
    }
}