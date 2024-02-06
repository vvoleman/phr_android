package cz.vvoleman.phr.common.utils

import android.view.View
import androidx.fragment.app.Fragment
import cz.vvoleman.phr.base.ui.mapper.BaseViewStateBinder

fun checkVisibility(condition: Boolean): Int {
    return if (condition) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun Fragment.checkVisibility(condition: Boolean): Int {
    return cz.vvoleman.phr.common.utils.checkVisibility(condition)
}

fun BaseViewStateBinder<*,*,*>.checkVisibility(condition: Boolean): Int {
    return cz.vvoleman.phr.common.utils.checkVisibility(condition)
}
