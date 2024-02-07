package cz.vvoleman.phr.base.ui.ext

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(block)
        }
    }
}
fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
    viewLifecycleOwner.lifecycleScope.launch {
        flow.collect(block)
    }
}

fun <T> AppCompatActivity.collectLatestLifecycleFlow(flow: Flow<T>, block: suspend ((T) -> Unit)) {
    lifecycleScope.launch {
        lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            flow.collectLatest(block)
        }
    }
}
