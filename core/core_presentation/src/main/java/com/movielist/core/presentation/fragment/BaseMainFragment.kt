package com.movielist.core.presentation.fragment

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

abstract class BaseMainFragment<B : ViewDataBinding>(
    @LayoutRes layoutResId: Int
) : ViewModelFragment<B>(layoutResId)