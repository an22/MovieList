package com.movielist.core.common.base.presentation.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.movielist.core.common.base.navigation.BaseNavigator
import com.movielist.core.common.base.presentation.viewmodel.BaseViewModelFactory
import javax.inject.Inject

abstract class BaseNavigatorActivity<Navigator : BaseNavigator, B : ViewDataBinding>(
    @LayoutRes layoutRes: Int
) : BaseActivity<B>(layoutRes) {

    abstract var navigator: Navigator

    @Inject
    lateinit var viewModeFactory: BaseViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        navigator.attachActivity(this)
    }

    override fun onDestroy() {
        navigator.activityDetached()
        super.onDestroy()
    }
}