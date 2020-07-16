package com.an2.core.common.base.navigation

import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.an2.core.R
import com.an2.core.common.base.presentation.fragment.BaseFragment
import java.lang.ref.WeakReference

abstract class BaseFragmentNavigator {

    private var fragmentWr: WeakReference<BaseFragment<*>>? = null

    protected val activity: BaseFragment<*>? get() = fragmentWr?.get()
    protected val fManager: FragmentManager? get() = activity?.childFragmentManager

    fun attachFragment(fragment: BaseFragment<*>) {
        fragmentWr = WeakReference(fragment)
    }

    fun fragmentDetached() {
        fragmentWr = null
    }

    //region animations
    fun FragmentTransaction.withSlideFromRightAnim() {
        setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right
        )
    }

    fun FragmentTransaction.withSlideFromRightNoalphaAnim() {
        setCustomAnimations(
                R.anim.slide_in_right_noalpha,
                R.anim.slide_out_left_noalpha,
                R.anim.slide_in_left_noalpha,
                R.anim.slide_out_right_noalpha
        )
    }

    fun FragmentTransaction.withSlideFromRightPopupAnim() {
        setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.no_anim,
                R.anim.no_anim,
                R.anim.slide_out_right
        )
    }
    //endregion
}