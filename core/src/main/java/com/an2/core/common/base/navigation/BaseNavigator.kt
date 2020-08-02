package com.an2.core.common.base.navigation

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.an2.core.R
import com.an2.core.common.base.presentation.activity.BaseActivity
import java.lang.ref.WeakReference

abstract class BaseNavigator {

    private var activityWr: WeakReference<BaseActivity<*>>? = null

    protected val activity: BaseActivity<*>? get() = activityWr?.get()
    protected val fManager: FragmentManager? get() = activity?.supportFragmentManager

    fun attachActivity(activity: BaseActivity<*>) {
        activityWr = WeakReference(activity)
    }

    fun activityDetached() {
        activityWr = null
    }

    fun addBackStackListenerWithCurrentFragment(containerId: Int, listener: (Fragment?) -> Unit) {
        fManager?.addOnBackStackChangedListener {
            listener(fManager?.findFragmentById(containerId))
        }
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

    fun FragmentTransaction.withSlideFromRightPopupAnim() {
        setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.no_anim,
                R.anim.no_anim,
                R.anim.slide_out_right
        )
    }
    //endregion

    fun FragmentTransaction.replaceWithTag(@IdRes containerId: Int, fragment: Fragment) {
        replace(
                containerId,
                fragment,
                fragment::class.java.simpleName
        )
    }
}