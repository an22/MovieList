package com.an2.core.common.base.presentation.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.ColorRes
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.an2.core.common.base.presentation.viewmodel.AlertMessageEvent
import com.an2.core.common.base.presentation.viewmodel.ErrorMessageEvent
import com.an2.core.common.base.presentation.viewmodel.Event
import com.an2.core.common.base.presentation.viewmodel.MessageEvent
import com.an2.core.extensions.decorate
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar

private const val MESSAGE_DURATION = 2_000

abstract class BaseActivity(@LayoutRes protected val layoutRes: Int) :
    AppCompatActivity(layoutRes) {

    private val mainView by lazy { window.decorView }

    @IdRes
    protected open val messagesContainer: Int = android.R.id.content

    override fun onCreate(savedInstanceState: Bundle?) {
        injectActivity()
        super.onCreate(savedInstanceState)
    }

    open fun injectActivity() {

    }

    @CallSuper
    protected open fun onEvent(event: Event) {
        when (event) {
            is MessageEvent -> showMessage(
                messageText = event.getMessageString(this),
                containerResId = messagesContainer,
                actionTitleId = null,
                action = null,
                duration = MESSAGE_DURATION
            )
            is ErrorMessageEvent -> showError(
                messageText = event.getMessageString(this),
                containerResId = messagesContainer,
                actionTitleId = null,
                action = null,
                duration = MESSAGE_DURATION,
                onDismiss = {}
            )
            is AlertMessageEvent -> showAlertMessage(
                event.getMessageString(this),
                event.onDismissListener
            )
        }
    }

    fun showAlertMessage(messageText: String, onDismissListener: () -> Unit = {}) {
        MaterialAlertDialogBuilder(this)
            .setMessage(messageText)
            .setPositiveButton(android.R.string.ok) { d, _ -> d.dismiss(); onDismissListener.invoke() }
            .setOnDismissListener { onDismissListener.invoke() }
            .show()
    }

    fun showMessage(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int
    ) {
        val snack = createSnack(
            mainView = mainView,
            messageText = messageText,
            backgroundColor = android.R.color.background_dark,
            textColor = android.R.color.white,
            containerResId = containerResId,
            duration = duration
        )

        actionTitleId?.let {
            snack?.setAction(it, action)
        }

        snack?.show()
    }

    fun showError(
        messageText: String,
        actionTitleId: Int?,
        action: ((View) -> Unit)?,
        containerResId: Int,
        duration: Int,
        onDismiss: (() -> Unit)?
    ) {
        val snack = createSnack(
            mainView = mainView,
            messageText = messageText,
            backgroundColor = android.R.color.holo_red_light,
            textColor = android.R.color.white,
            containerResId = containerResId,
            duration = duration
        )

        actionTitleId?.let {
            snack?.setAction(it, action)
        }

        snack?.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                onDismiss?.invoke()
            }
        })

        snack?.show()
    }


    @Suppress("LongParameterList")
    private fun createSnack(
        mainView: View,
        messageText: String,
        @ColorRes backgroundColor: Int,
        @ColorRes textColor: Int,
        containerResId: Int,
        duration: Int
    ): Snackbar? {
        val viewGroup = mainView.findViewById(containerResId) as ViewGroup?

        return viewGroup?.let {
            Snackbar
                .make(viewGroup, messageText, duration)
                .decorate(backgroundColor, textColor)
        }
    }

}