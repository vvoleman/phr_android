package cz.vvoleman.phr.base.ui.view

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import cz.vvoleman.phr.base.presentation.model.PresentationDestination
import cz.vvoleman.phr.base.presentation.viewmodel.BaseViewModel
import cz.vvoleman.phr.base.ui.ext.collectLatestLifecycleFlow
import cz.vvoleman.phr.base.ui.ext.collectLifecycleFlow
import cz.vvoleman.phr.base.ui.mapper.DestinationUiMapper
import cz.vvoleman.phr.base.ui.mapper.ViewStateBinder
import kotlinx.coroutines.launch

abstract class BaseFragment<VIEW_STATE : Any, NOTIFICATION : Any, VIEW_BINDING : ViewBinding> :
    Fragment() {

    protected abstract val viewModel: BaseViewModel<VIEW_STATE, NOTIFICATION>

    @Suppress("VariableNaming")
    open val TAG = "BaseFragment"

    abstract val destinationMapper: DestinationUiMapper
    abstract val viewStateBinder: ViewStateBinder<VIEW_STATE, VIEW_BINDING>
    private var _binding: VIEW_BINDING? = null
    protected val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (viewModel.viewState.value == null) {
            lifecycleScope.launch {
                viewModel.onInit()
            }
        }
    }

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = setupBinding(inflater, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuId = setOptionsMenu()
        if (menuId != null) {
            setupOptionsMenu(menuId)
        }

        viewStateBinder.init(binding, requireContext(), lifecycleScope)
        setupListeners()
        observeViewModel()
    }

    protected abstract fun setupBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): VIEW_BINDING

    protected open fun setupListeners() {}

    protected open fun setOptionsMenu(): Int? = null

    protected open fun onOptionsMenuItemSelected(menuItem: MenuItem): Boolean = false

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

    protected fun showConfirmDialog(
        title: Int,
        message: Int,
        positiveAction: Pair<Int, (DialogInterface) -> Unit>,
        negativeAction: Pair<Int, (DialogInterface) -> Unit>
    ) {
        showConfirmDialog(
            getString(title),
            getString(message),
            Pair(getString(positiveAction.first), positiveAction.second),
            Pair(getString(negativeAction.first), negativeAction.second),
        )
    }

    protected fun showSnackbar(
        message: String,
        length: Int = Snackbar.LENGTH_SHORT,
        actions: List<Pair<String, () -> Unit>> = emptyList()
    ) {
        val snackbar = Snackbar.make(binding.root, message, length)

        actions.forEach { action ->
            snackbar.setAction(action.first) {
                action.second.invoke()
            }
        }

        snackbar.show()
    }

    protected fun showSnackbar(
        message: Int,
        length: Int = Snackbar.LENGTH_SHORT,
        actions: List<Pair<String, () -> Unit>> = emptyList()
    ) {
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

    private fun setupOptionsMenu(menuId: Int) {
        val menuHost = requireActivity() as MenuHost
        menuHost.addMenuProvider(
            object : MenuProvider {
                override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                    menuInflater.inflate(menuId, menu)
                }

                override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                    return onOptionsMenuItemSelected(menuItem)
                }
            },
            viewLifecycleOwner,
            Lifecycle.State.RESUMED
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewStateBinder.onDestroy(binding)
        _binding = null
    }
}
