package cz.vvoleman.phr.base.ui.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

abstract class BaseFragment<VIEW_STATE : Any, NOTIFICATION : Any> : Fragment() {

    protected abstract val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>

    open val TAG = "BaseFragment"

    abstract val destinationMapper: DestinationUiMapper
    abstract val viewStateBinders: List<ViewStateBinder<VIEW_STATE, ViewBinding>>
    private var _binding: ViewBinding? = null
    protected val binding get() = _binding!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupBinding(inflater, container)
        observeViewModel()

        return super.onCreateView(inflater, container, savedInstanceState)
    }

    protected abstract fun setupBinding(inflater: LayoutInflater, container: ViewGroup?): ViewBinding

    private fun observeViewModel() {
        collectLatestLifecycleFlow(viewModel.viewState) {
            it?.let { applyViewState(it) }
        }
        collectLifecycleFlow(viewModel.notification) {
            showNotification(it)
        }
        collectLifecycleFlow(viewModel.destination) {
            navigateToDestination(it)
        }
    }

    private fun applyViewState(viewState: VIEW_STATE) {
        for (binder in viewStateBinders) {
            binder.bind(binding, viewState)
        }
    }

    private fun showNotification(notification: NOTIFICATION) {
        Log.d(TAG, "showNotification: $notification")
    }

    private fun navigateToDestination(destination: PresentationDestination) {
        destinationMapper.navigate(destination)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        val NO_LAYOUT_RESOURCE = 0
    }

    private fun <T> Fragment.collectLatestLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collectLatest(block)
        }
    }

    private fun <T> Fragment.collectLifecycleFlow(flow: Flow<T>, block: (T) -> Unit) {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            flow.collect(block)
        }
    }
}