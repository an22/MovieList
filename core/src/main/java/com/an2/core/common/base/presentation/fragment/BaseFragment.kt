package com.an2.core.common.base.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.an2.core.common.base.di.CoreComponent
import com.an2.core.common.base.presentation.activity.BaseActivity
import com.an2.core.common.base.presentation.viewmodel.AlertMessageEvent
import com.an2.core.common.base.presentation.viewmodel.ErrorMessageEvent
import com.an2.core.common.base.presentation.viewmodel.Event
import com.an2.core.common.base.presentation.viewmodel.MessageEvent
import com.an2.core.extensions.hideKeyboard
import com.an2.core.extensions.requireCoreComponent
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.*

private const val MESSAGE_DURATION = 2_000

@Suppress("MemberVisibilityCanBePrivate")
abstract class BaseFragment<B : ViewDataBinding>(
    @LayoutRes protected val layoutResId: Int
) : Fragment() {

    private val job: Job = SupervisorJob()
    protected val fragmentScope: CoroutineScope = CoroutineScope(Dispatchers.Main + job)


    protected val appCompatActivity: AppCompatActivity? get() = (activity as? AppCompatActivity)
    protected val coreComponent: CoreComponent
        get() = requireCoreComponent()

    protected var binding: B by AutoClearedValue()
        private set

    @IdRes
    protected open val messagesContainer: Int = android.R.id.content

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            lifecycleOwner = this@BaseFragment
        }
        viewCreated(savedInstanceState)
    }

    abstract fun viewCreated(savedInstanceState: Bundle?)

    override fun onPause() {
        activity?.hideKeyboard()
        super.onPause()
    }

    override fun onDestroy() {
        fragmentScope.cancel()
        super.onDestroy()
    }

    @CallSuper
    protected open fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(
                messageText = event.getMessageString(requireContext()),
                containerResId = messagesContainer
            )
            is ErrorMessageEvent -> showError(
                messageText = event.getMessageString(requireContext()),
                containerResId = messagesContainer
            )
            is AlertMessageEvent -> showAlertMessage(
                event.getMessageString(requireContext()),
                event.onDismissListener
            )
        }
    }

    fun showAlertMessage(messageText: String, onDismissListener: () -> Unit = {}) {
        MaterialAlertDialogBuilder(requireContext())
            .setMessage(messageText)
            .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss(); onDismissListener.invoke() }
            .setOnDismissListener { onDismissListener.invoke() }
            .show()
    }

    fun showMessage(messageText: String, containerResId: Int) {
        (appCompatActivity as BaseActivity<*>).showMessage(
            messageText,
            null,
            null,
            containerResId,
            MESSAGE_DURATION
        )
    }

    fun showError(messageText: String, containerResId: Int) {
        (appCompatActivity as BaseActivity<*>).showError(
            messageText,
            null,
            null,
            containerResId,
            MESSAGE_DURATION,
            {}
        )
    }
}