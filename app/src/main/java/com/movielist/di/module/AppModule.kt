package com.movielist.di.module

import android.content.Context
import com.movielist.app.MovieListApp
import dagger.Module
import dagger.Provides

@Module
internal class AppModule {
    @Provides
    fun provideContext(app: MovieListApp): Context {
        return app
    }
}