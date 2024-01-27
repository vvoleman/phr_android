package cz.vvoleman.phr.common.ui.component.picker

import androidx.fragment.app.FragmentManager

interface PickerListener {
    fun injectFragmentManager(): FragmentManager
}
