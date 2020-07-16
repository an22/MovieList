package com.movielist.di

import com.movielist.app.MovieListApp
import com.movielist.di.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
        modules = [
            AppModule::class,
            AndroidInjectionModule::class
        ]
)
interface AppComponent : AndroidInjector<MovieListApp> {
    @Component.Factory
    interface Factory : AndroidInjector.Factory<MovieListApp>
}