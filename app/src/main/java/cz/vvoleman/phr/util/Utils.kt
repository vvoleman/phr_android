package cz.vvoleman.phr.util

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

val <T> T.exhaustive: T
    get() = this

// lifecycle

fun <T> Fragment.collectLatestLifecycleFlow(
    flow: Flow<T>,
    block: (T) -> Unit
) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.collectLatest(block)
    }
}
