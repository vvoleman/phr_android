package cz.vvoleman.phr.common.utils

import android.view.View
import androidx.fragment.app.Fragment

fun Fragment.checkVisibility(condition: Boolean): Int {
    return if (condition) {
        View.VISIBLE
    } else {
        View.GONE
    }
}
