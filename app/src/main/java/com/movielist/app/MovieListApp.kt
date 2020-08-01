package com.movielist.app

import android.app.Application
import com.an2.core.common.base.di.CoreComponent
import com.an2.core.common.base.di.DaggerCoreComponent
import com.an2.core.common.base.di.holder.ComponentHolder

@Suppress("unused")
class MovieListApp : Application(), ComponentHolder<CoreComponent> {
    override val component: CoreComponent by lazy {
        DaggerCoreComponent
                .factory()
                .create(this)
    }
}
