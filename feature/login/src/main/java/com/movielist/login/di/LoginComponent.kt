package com.movielist.login.di

import com.movielist.core.common.base.di.CoreComponent
import com.movielist.core.common.base.di.scope.ActivityScope
import com.movielist.login.presentation.LoginActivity
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

    fun inject(fragment: LoginActivity)
}