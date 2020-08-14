package com.movielist.core.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movielist.core.di.CoreComponent
import com.movielist.core.di.holder.ComponentHolder

@Suppress("unused")
@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(
        noinline ownerProducer: () -> Fragment = { this },
        noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
        VM::class,
        { ownerProducer().requireParentFragment().viewModelStore },
        factoryProducer
)

@Suppress("unchecked_cast")
fun Fragment.requireCoreComponent(): CoreComponent {
    try {
        return (requireActivity().applicationContext as ComponentHolder<CoreComponent>)
                .component
    } catch (e: ClassCastException) {
        throw ClassCastException(
                "Expected that Application class is extend ComponentHolder<CoreComponent> interface!"
        )
    }
}