package com.an2.core.common.base.presentation.fragment

import android.content.Context
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

abstract class ViewModelFragment<B : ViewDataBinding>(
    @LayoutRes layoutRes: Int
) : BaseFragment<B>(layoutRes) {

    @Inject
    protected lateinit var viewModeFactory: ViewModelProvider.Factory

    override fun onAttach(context: Context) {
        super.onAttach(context)
        injectFragment()
    }

    open fun injectFragment() {
        //nothing
    }
}