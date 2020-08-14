package com.movielist.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject
import javax.inject.Provider

class BaseViewModelFactory @Inject constructor(
    private val viewModelProviders: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        viewModelProviders[modelClass]?.let { provider ->
            return provider.get() as T
        } ?: run {
            return viewModelProviders.entries.firstOrNull {
                modelClass.isAssignableFrom(it.key)
            }?.value?.get() as T ?: throw IllegalStateException(
                "Provider not found for class ${modelClass.simpleName}, make sure you create provider for this ViewModel type"
            )
        }
    }
}