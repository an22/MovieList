package com.an2.core.common.base.presentation.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.an2.core.common.base.navigation.BaseNavigator
import com.an2.core.common.base.presentation.viewmodel.BaseViewModelFactory
import javax.inject.Inject

abstract class BaseNavigatorActivity<Navigator : BaseNavigator>(
    @LayoutRes layoutRes: Int
) : BaseActivity(layoutRes) {

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