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

abstract class BaseFragment<VIEW_STATE : Any, NOTIFICATION : Any, VIEW_BINDING : ViewBinding> :
    Fragment() {

    protected abstract val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>

    open val TAG = "BaseFragment"

    abstract val destinationMapper: DestinationUiMapper
    abstract val viewStateBinder: ViewStateBinder<VIEW_STATE, VIEW_BINDING>
    private var _binding: VIEW_BINDING? = null
    protected val binding get() = _binding!!

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupBinding(inflater, container)
        observeViewModel()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    protected abstract fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VIEW_BINDING

    private fun observeViewModel() {
        collectLatestLifecycleFlow(viewModel.viewState) {
            it?.let { applyViewState(it) }
        }
        collectLifecycleFlow(viewModel.notification) {
            handleNotification(it)
        }
        collectLifecycleFlow(viewModel.destination) {
            navigateToDestination(it)
        }
    }

    private fun applyViewState(viewState: VIEW_STATE) {
        viewStateBinder.bind(binding, viewState)
    }

    protected abstract fun handleNotification(notification: NOTIFICATION)

    private fun navigateToDestination(destination: PresentationDestination) {
        destinationMapper.navigate(destination)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
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