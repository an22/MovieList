package com.movielist.core.utils

import androidx.viewpager2.widget.ViewPager2

class ViewPagerScroller(private val viewPager2: ViewPager2) {

    init {
        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                isCanScrollLeft = !isFirstPage(position)
                isCanScrollRight = !isLastPage(position)
            }
        })
    }

    var isCanScrollLeft: Boolean = false
        private set(value) {
            if (field != value) {
                field = value
                onPageChanged.invoke()
            }
        }
        get() = !isFirstPage(viewPager2.currentItem)

    var isCanScrollRight: Boolean = false
        private set(value) {
            if (field != value) {
                field = value
                onPageChanged.invoke()
            }
        }
        get() = !isLastPage(viewPager2.currentItem)

    var onPageChanged: () -> Unit = {}

    fun scrollLeft() {
        viewPager2.currentItem = viewPager2.currentItem - 1
    }

    fun scrollRight() {
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun isLastPage(position: Int): Boolean {
        return viewPager2.adapter?.itemCount?.let { (it - 1) == position } ?: true
    }

    private fun isFirstPage(position: Int): Boolean {
        return position < 1
    }
}