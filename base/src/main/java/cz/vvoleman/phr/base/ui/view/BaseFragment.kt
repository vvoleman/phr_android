package cz.vvoleman.phr.base.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder

abstract class BaseFragment<VIEW_STATE : Any, NOTIFICATION : Any, VIEW_BINDING : ViewBinding> :
    Fragment() {

    protected abstract val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>

    @Suppress("VariableNaming")
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
        viewModel.onInit()
        _binding = setupBinding(inflater, container)
        viewStateBinder.init(binding, requireContext(), lifecycleScope)
        setupListeners()
        observeViewModel()
        return binding.root
    }

    protected abstract fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VIEW_BINDING

    protected open fun setupListeners() {}

    protected fun showConfirmDialog(
        title: String,
        message: String,
        positiveAction: Pair<String, (DialogInterface) -> Unit>,
        negativeAction: Pair<String, (DialogInterface) -> Unit>
    ) {
        val alertDialogBuilder = AlertDialog.Builder(context)

        alertDialogBuilder.setTitle(title)
        alertDialogBuilder.setMessage(message)

        alertDialogBuilder.setPositiveButton(positiveAction.first) { dialog, _ ->
            positiveAction.second.invoke(dialog)
        }

        alertDialogBuilder.setNegativeButton(negativeAction.first) { dialog, _ ->
            negativeAction.second.invoke(dialog)
        }

        val alertDialog: AlertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }

    protected fun showSnackbar(message: String, length: Int = Snackbar.LENGTH_SHORT, actions: List<Pair<String, () -> Unit>> = emptyList()) {
        val snackbar = Snackbar.make(binding.root, message, length)

        actions.forEach { action ->
            snackbar.setAction(action.first) {
                action.second.invoke()
            }
        }

        snackbar.show()
    }

    protected fun showSnackbar(message: Int, length: Int = Snackbar.LENGTH_SHORT, actions: List<Pair<String, () -> Unit>> = emptyList()) {
        showSnackbar(binding.root.resources.getString(message), length, actions)
    }

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
}
