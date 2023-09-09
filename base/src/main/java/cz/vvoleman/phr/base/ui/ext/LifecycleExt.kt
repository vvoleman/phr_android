package cz.vvoleman.phr.base.ui.ext

import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launchWhenStarted {
        flow.collectLatest(block)
    }
}
fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collect(block)
    }
}
