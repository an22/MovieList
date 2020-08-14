package com.movielist.app

import android.app.Application
import com.movielist.core.common.base.di.CoreComponent
import com.movielist.core.common.base.di.DaggerCoreComponent
import com.movielist.core.common.base.di.holder.ComponentHolder

@Suppress("unused")
class MovieListApp : Application(), ComponentHolder<CoreComponent> {
    override val component: CoreComponent by lazy {
        DaggerCoreComponent
                .factory()
                .create(this)
    }
}
