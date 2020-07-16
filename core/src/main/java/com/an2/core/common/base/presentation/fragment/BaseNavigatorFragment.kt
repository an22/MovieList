package com.an2.core.common.base.presentation.fragment

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.an2.core.common.base.navigation.BaseFragmentNavigator

abstract class BaseNavigatorFragment<Navigator : BaseFragmentNavigator, B : ViewDataBinding>(
    @LayoutRes layoutRes: Int
) : ViewModelFragment<B>(layoutRes) {

    abstract var navigator: Navigator

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        navigator.attachFragment(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        navigator.fragmentDetached()
        super.onDestroyView()
    }
}