package com.an2.core.common.base.di

import android.content.Context
import com.an2.core.common.base.di.module.CoreModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            CoreModule::class
        ]
)
interface CoreComponent {
    @Component.Factory
    interface Factory {
        fun create(@BindsInstance appContext: Context): CoreComponent
    }
}