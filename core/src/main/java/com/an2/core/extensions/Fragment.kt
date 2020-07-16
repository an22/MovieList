package com.an2.core.extensions

import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.fragment.app.createViewModelLazy
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@MainThread
inline fun <reified VM : ViewModel> Fragment.parentViewModels(
    noinline ownerProducer: () -> Fragment = { this },
    noinline factoryProducer: (() -> ViewModelProvider.Factory)? = null
) = createViewModelLazy(
    VM::class,
    { ownerProducer().requireParentFragment().viewModelStore },
    factoryProducer
)